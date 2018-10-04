package ru.grishagin.components;


import com.badlogic.ashley.core.Component;

//End point of entity's journey
//
public class DirectionComponent implements Component {
    public float x;
    public float y;

    public DirectionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
