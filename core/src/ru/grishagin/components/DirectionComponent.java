package ru.grishagin.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

//End point of entity's journey
//
public class DirectionComponent implements Component {
    public float x;
    public float y;

    public DirectionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //set another entity as destination
    public DirectionComponent(Entity target){
        PositionComponent destinationPosition = target.getComponent(PositionComponent.class);
        x = destinationPosition.x;
        y = destinationPosition.y;
    }
}
