package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.DestinationComponent;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.VelocityComponent;
import ru.grishagin.model.map.TiledBasedMap;
import ru.grishagin.systems.patfinding.*;
import ru.grishagin.utils.Logger;

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<DestinationComponent> dm = ComponentMapper.getFor(DestinationComponent.class);

    TiledGraph<FlatTiledNode> mapGraph;
    TiledManhattanDistance<FlatTiledNode> heuristic;
    IndexedAStarPathFinder<FlatTiledNode> pathFinder;
    PathSmoother<FlatTiledNode, Vector2> pathSmoother;

    public MovementSystem(){
        super(Family.all(DestinationComponent.class, PositionComponent.class, VelocityComponent.class).get());
    }

    public void setMap(TiledBasedMap map){
        //convert map to graph
        mapGraph = new FlatTiledGraph();
        mapGraph.init(map);

        heuristic = new TiledManhattanDistance<FlatTiledNode>();
        //pathSmoother = new PathSmoother<FlatTiledNode, Vector2>(new TiledRaycastCollisionDetector<FlatTiledNode>());
        pathFinder = new IndexedAStarPathFinder<FlatTiledNode>(mapGraph, true);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);
        DestinationComponent destination = dm.get(entity);

        if(destination.path == null){
            TiledSmoothableGraphPath path = buildPath(position, destination);
            destination.path = path;
            Logger.info("Path for " + entity.getComponent(NameComponent.class) + " is built");
        }

        if(position.x == destination.x && position.y == destination.y){
            stop(entity);
        } else {
            followPath(entity);
        }

        position.x += velocity.x*deltaTime;
        position.y += velocity.y*deltaTime;
    }

    private void followPath(Entity entity){
        PositionComponent position = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);
        DestinationComponent destination = dm.get(entity);

        FlatTiledNode currentNode = mapGraph.getNode((int)position.x, (int)position.y);
        for (int i = 0; i < destination.path.nodes.size - 1; i++) { //entity cannot follow path if it has position == destination
            FlatTiledNode node = destination.path.nodes.get(i);
            //find node in the path where entity currently is standing on
            if(node.x == currentNode.x && node.y == currentNode.y){
                FlatTiledNode nextPathNode = destination.path.nodes.get(i + 1);

                float deltaX = Math.abs(nextPathNode.x - position.x);
                float deltaY = Math.abs(nextPathNode.y - position.y);
                float distance = (float)Math.sqrt(deltaX*deltaX + deltaY*deltaY);

                if(distance != 0) {
                    float sin, cos;
                    sin = deltaY / distance;
                    cos = deltaX / distance;

                    velocity.x = velocity.speed * cos;
                    velocity.y = velocity.speed * sin;

                    if (Math.abs(nextPathNode.x - position.x) > 0.1f) {
                        if (nextPathNode.x - position.x < 0) {
                            velocity.x = -velocity.x;
                        }
                    } else {
                        position.x = nextPathNode.x;
                        velocity.x = 0;
                    }

                    if (Math.abs(nextPathNode.y - position.y) > 0.1f) {
                        if (nextPathNode.y - position.y < 0) {
                            velocity.y = -velocity.y;
                        }
                    } else {
                        position.y = nextPathNode.y;
                        velocity.y = 0;
                    }
                }
            }
        }
    }

    private void stop(Entity entity){
        entity.remove(DestinationComponent.class);
        VelocityComponent velocity = vm.get(entity);
        velocity.x = 0;
        velocity.y = 0;
    }

    private TiledSmoothableGraphPath buildPath(PositionComponent position, DestinationComponent destination){
        FlatTiledNode startNode = mapGraph.getNode((int)position.x, (int)position.y);
        FlatTiledNode endNode = mapGraph.getNode((int)destination.x, (int)destination.y);

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<FlatTiledNode>();
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        return path;
    }
}
