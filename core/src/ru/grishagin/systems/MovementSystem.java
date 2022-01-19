package ru.grishagin.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.*;
import ru.grishagin.components.tags.DoorTag;
import ru.grishagin.components.tags.ImpassableComponent;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.model.GameModel;
import ru.grishagin.model.map.TiledBasedMap;
import ru.grishagin.model.messages.MessageType;
import ru.grishagin.systems.patfinding.*;
import ru.grishagin.utils.Logger;

import static ru.grishagin.model.map.TiledBasedMap.ROOF_LAYER;

public class MovementSystem extends IteratingSystem implements Telegraph {
    private static final float STOP_PRECISION = 0.1f;

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

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        engine.addEntityListener(getFamily(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                MessageManager.getInstance().dispatchMessage(MessageType.START_MVMNT, entity);
            }

            @Override
            public void entityRemoved(Entity entity) {
                //entity leaves movement family - dispatch stop message (to stop movement animation)
                MessageManager.getInstance().dispatchMessage(MessageType.STOP_MVMNT, entity);
            }
        });
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

        velocity.x = 0;
        velocity.y = 0;

        if(destination.path == null){
            TiledSmoothableGraphPath<FlatTiledNode> path = buildPath(position, destination);
            if(path.nodes.notEmpty()){
                destination.path = path;
                Logger.info("Path for " + entity.getComponent(NameComponent.class) + " is built. Destination is " + destination.x + ", " + destination.y);
            } else{
                //empty path means destination is not accessible
                stop(entity);
                return;
            }
        }

        if(Math.abs(position.x - destination.x) < STOP_PRECISION && Math.abs(position.y - destination.y) < STOP_PRECISION){
            stop(entity);
            position.x = destination.x;
            position.y = destination.y;
        } else {
            followPath(entity);
        }

        position.x += velocity.x*deltaTime;
        position.y += velocity.y*deltaTime;

        setOrientation(entity, velocity.x, velocity.y);

        if(entity.getComponent(PlayerControlled.class) != null){
            showHideRoof(position);
        }
    }

    private void followPath(Entity entity){
        PositionComponent position = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);
        DestinationComponent destination = dm.get(entity);

        FlatTiledNode currentNode = mapGraph.getNode((int)position.x, (int)position.y);
        for (int i = 0; i < destination.path.nodes.size; i++) { //entity cannot follow path if it has position == destination
            FlatTiledNode node = destination.path.nodes.get(i);
            //find node in the path where entity currently is standing on
            if(node.x == currentNode.x && node.y == currentNode.y){
                FlatTiledNode nextPathNode;
                if(i == destination.path.nodes.size - 1){
                    nextPathNode = destination.path.nodes.peek();
                } else {
                    nextPathNode = destination.path.nodes.get(i + 1);
                }

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

    private TiledSmoothableGraphPath<FlatTiledNode> buildPath(PositionComponent position, DestinationComponent destination){
        FlatTiledNode startNode = mapGraph.getNode((int)position.x, (int)position.y);
        FlatTiledNode endNode = mapGraph.getNode((int)destination.x, (int)destination.y);

        TiledSmoothableGraphPath<FlatTiledNode> path = new TiledSmoothableGraphPath<FlatTiledNode>();

        //if end node is unavailable, build path for the closest available
        if(endNode.getType() == TileNodeType.IMPASSABLE){
            mapGraph.changeNodeType(endNode.getIndex(), TileNodeType.NORMAL);
            pathFinder.searchNodePath(startNode, endNode, heuristic, path);
            endNode = path.nodes.get(path.nodes.size - 2);//previous before last (impassable) node
            mapGraph.changeNodeType(mapGraph.getNode((int)destination.x, (int)destination.y).getIndex(), TileNodeType.IMPASSABLE);//change type back
            path.clear();

            destination.x = endNode.x;
            destination.y = endNode.y;
        }

        pathFinder.searchNodePath(startNode, endNode, heuristic, path);

        DebugSystem drawer = getEngine().getSystem(DebugSystem.class);
        drawer.clear();
        for (FlatTiledNode node : path) {
            drawer.addRect(new Vector2(node.x, node.y));
        }

        return path;
    }

    //roof layer has vertical offset
    private void showHideRoof(PositionComponent playerPosition){
        TiledBasedMap currentMap = GameModel.instance.getCurrentMap();
        if(currentMap.hasObject(playerPosition.x, playerPosition.y, ROOF_LAYER)){
            currentMap.setLayerVisibility(ROOF_LAYER, false);
        } else {
            currentMap.setLayerVisibility(ROOF_LAYER, true);
        }
    }

    //orientation in screen coordinates
    private void setOrientation(Entity entity, float velocityX, float velocityY){
        OrientationComponent.Orientation orientation;
        if(velocityX > 0){
            if(velocityY > 0) {
                orientation = OrientationComponent.Orientation.E;
            } else if(velocityY < 0){
                orientation = OrientationComponent.Orientation.S;
            } else {
                orientation = OrientationComponent.Orientation.SE;
            }
        } else if (velocityX < 0){
            if(velocityY > 0) {
                orientation = OrientationComponent.Orientation.N;
            } else if(velocityY < 0){
                orientation = OrientationComponent.Orientation.W;
            } else {
                orientation = OrientationComponent.Orientation.NW;
            }
        } else {
            if(velocityY > 0) {
                orientation = OrientationComponent.Orientation.NE;
            } else if(velocityY < 0){
                orientation = OrientationComponent.Orientation.SW;
            } else {
                //orientation remains the same
                return;
            }
        }

        Logger.info(orientation.toString());

        OrientationComponent orientationComponent = entity.getComponent(OrientationComponent.class);
        if(orientationComponent != null){
            OrientationComponent.Orientation oldOrientation = orientationComponent.orientation;
            orientationComponent.orientation = orientation;
            if(oldOrientation != orientation){
                MessageManager.getInstance().dispatchMessage(MessageType.ORIENTATION_CHANGE, entity);
            }
        } else {
            entity.add(new OrientationComponent(orientation));
        }
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        if(msg.extraInfo != null){
            Entity entity = (Entity)msg.extraInfo;
            PositionComponent position = pm.get(entity);
            FlatTiledNode node = mapGraph.getNode((int)position.x, (int)position.y);

            switch (msg.message){
                case MessageType.CLOSED:
                case MessageType.OPENED:
                    ImpassableComponent impassableComponent = entity.getComponent(ImpassableComponent.class);
                    DoorTag isDoor = entity.getComponent(DoorTag.class);
                    if(isDoor != null){//make sure it is a door
                        if(impassableComponent == null){
                            entity.add(new ImpassableComponent());
                            mapGraph.changeNodeType(node.getIndex(), TileNodeType.IMPASSABLE);
                        } else {
                            entity.remove(ImpassableComponent.class);
                            mapGraph.changeNodeType(node.getIndex(), TileNodeType.NORMAL);
                        }
                    }
                    break;
            }
        }
        return true;
    }
}
