package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TextureComponent implements Component {
    public TextureRegion region = null;
    public Vector2 renderingSize = new Vector2();
    public Vector2 offset = new Vector2();

    public TextureComponent(TextureRegion region) {
        this.region = region;
        renderingSize.x = region.getRegionWidth();
        renderingSize.y = region.getRegionHeight();
    }

    public TextureComponent(TextureRegion region, int width, int height) {
        this.region = region;
        renderingSize.x = width;
        renderingSize.y = height;
    }
}
