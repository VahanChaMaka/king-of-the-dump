package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class FloatingTextComponent implements Component {

    private String text;
    private Vector2 offset;

    public FloatingTextComponent(String text) {
        this.text = text;
        offset = new Vector2(10, 30);
    }

    public String getText() {
        return text;
    }

    public Vector2 getOffset() {
        return offset;
    }
}
