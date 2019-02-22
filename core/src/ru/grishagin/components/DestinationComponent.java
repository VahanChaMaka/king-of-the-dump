package ru.grishagin.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.systems.patfinding.FlatTiledNode;
import ru.grishagin.systems.patfinding.TiledNode;
import ru.grishagin.systems.patfinding.TiledSmoothableGraphPath;

//End point of entity's journey
//
public class DestinationComponent implements Component {
    //x and y are final to make sure new path will be created
    public final float x;
    public final float y;

    public TiledSmoothableGraphPath<FlatTiledNode> path;

    public DestinationComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //set another entity as destination
    public DestinationComponent(Entity target){
        PositionComponent destinationPosition = target.getComponent(PositionComponent.class);
        x = destinationPosition.x;
        y = destinationPosition.y;
    }
}
