package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    public float x;
    public float y;

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PositionComponent(Vector2 position){
        this.x = position.x;
        this.y = position.y;
    }

    public Vector2 getPosition(){
        return new Vector2(x, y);
    }
}
