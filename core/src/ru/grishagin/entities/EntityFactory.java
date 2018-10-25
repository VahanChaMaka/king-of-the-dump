package ru.grishagin.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.grishagin.components.*;
import ru.grishagin.components.stats.*;
import ru.grishagin.components.tags.ImpassableComponent;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.model.actions.TransferAction;
import ru.grishagin.utils.AssetManager;

public class EntityFactory {


    public static Entity makePlayer(int x, int y){
        Entity entity = new Entity();
        entity.add(new PlayerControlled());
        entity.add(new PositionComponent(x, y));
        entity.add(new VelocityComponent(5f));
        entity.add(new InventoryComponent(100000));

        entity.add(new HealthComponent(100));
        entity.add(new HungerComponent());
        entity.add(new ThirstComponent());
        entity.add(new FatigueComponent());
        entity.add(new RadDoseComponent());
        entity.add(new ToxicityDoseComponent());

        TextureComponent textureComponent = new TextureComponent(
                new TextureRegion(AssetManager.instance.getTexture("player.png")), 32, 32);
        textureComponent.offset.x = -16;
        textureComponent.offset.y = 16;
        entity.add(textureComponent);

        return entity;
    }

    public static Entity makeChest(int x, int y){
        Entity entity = new Entity();
        entity.add(new PositionComponent(x, y));
        entity.add(new TextureComponent(new TextureRegion(
                AssetManager.instance.getTexture("tiles/grassland_tiles.png"), 0, 284, 64, 32)));
        entity.add(new InteractiveComponent(new TransferAction()));
        entity.add(new InventoryComponent(1000));
        entity.add(new ImpassableComponent());

        return entity;
    }
}
