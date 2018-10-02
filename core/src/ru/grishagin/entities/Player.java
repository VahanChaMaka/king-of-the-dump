package ru.grishagin.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.grishagin.components.DirectionComponent;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.SpeedComponent;
import ru.grishagin.components.TextureComponent;
import ru.grishagin.utils.AssetManager;

public class Player extends Entity {
    public Player(){
        add(new PositionComponent(1, 3));
        add(new SpeedComponent(0.1f));
        add(new DirectionComponent());
        add(new TextureComponent(new TextureRegion(AssetManager.instance.getTexture("player.png"))));
    }
}
