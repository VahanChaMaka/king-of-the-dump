package ru.grishagin.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.grishagin.components.InteractiveComponent;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.TextureComponent;
import ru.grishagin.model.actions.TransferAction;
import ru.grishagin.utils.AssetManager;

public class Chest extends Entity {
    public Chest(){
        add(new PositionComponent(10, 10));
        add(new TextureComponent(new TextureRegion(
                AssetManager.instance.getTexture("tiles/grassland_tiles.png"), 17, 284, 33, 30)));
        add(new InteractiveComponent(new TransferAction()));
    }
}
