package ru.grishagin.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.grishagin.components.*;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.utils.AssetManager;

public class Player extends Entity {
    public Player(){
        add(new PlayerControlled());
        add(new PositionComponent(1, 3));
        add(new VelocityComponent(0.1f));
        add(new InventoryComponent());

        TextureComponent textureComponent = new TextureComponent(
                new TextureRegion(AssetManager.instance.getTexture("player.png")), 32, 32);
        textureComponent.offset.x = -16;
        add(textureComponent);
    }
}
