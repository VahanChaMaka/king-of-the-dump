package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteComponent implements Component {
    public Sprite sprite = null;
    public Vector2 offset = new Vector2();
    public int renderingOrder = 2; //greater thw number - later rendering

    public SpriteComponent(Sprite region) {
        this.sprite = region;
    }
}
