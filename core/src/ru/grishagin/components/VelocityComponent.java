package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public float x;
    public float y;

    public float speed;

    public VelocityComponent(float speed) {
        this.speed = speed;
    }
}
