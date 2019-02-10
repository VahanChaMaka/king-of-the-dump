package ru.grishagin.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.*;
import ru.grishagin.components.stats.*;
import ru.grishagin.components.tags.HostileTag;
import ru.grishagin.components.tags.ImpassableComponent;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.model.GameModel;
import ru.grishagin.model.actions.Action;
import ru.grishagin.model.actions.TransferAction;
import ru.grishagin.utils.AssetManager;

import java.util.Iterator;

public class EntityFactory {

    private static final String NAME = "name";
    private static final String ACTION = "action";
    private static final String INVENTORY = "inventory";
    private static final String ITEMS = "items";
    private static final String GID = "gid";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String ID = "id";
    private static final String X = "x";
    private static final String Y = "y";

    private static final String TRANSFER_ACTION = "transfer";

    private static ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);

    public static Entity makePlayer(int x, int y){
        Entity entity = new Entity();
        entity.add(new NameComponent("Player"));
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

        Sprite sprite = new Sprite(AssetManager.instance.getTexture("player.png"));
        sprite.setSize(32, 32);
        SpriteComponent spriteComponent = new SpriteComponent(sprite);
        spriteComponent.offset.x = -16;
        spriteComponent.offset.y = 16;
        entity.add(spriteComponent);

        entity.add(new ShaderComponent(ShaderType.OUTLINE));

        return entity;
    }

    public static Entity makeNPC(){
        Entity entity = makeBasicNPC();

        entity.add(new SpriteComponent(new Sprite(AssetManager.instance.getTexture("npc/rathound.png"))));
        entity.add(new HostileTag());

        return postProcessNPC(entity);
    }

    private static Entity makeBasicNPC(){
        Entity entity = new Entity();

        entity.add(new PositionComponent(10, 10));
        entity.add(new HealthComponent(100));
        entity.add(new ImpassableComponent());

        return entity;
    }

    private static Entity postProcessNPC(Entity entity){
        if(entity.getComponent(HostileTag.class) != null){
            entity.add(new ShaderComponent(ShaderType.OUTLINE));
        } else if(entity.getComponent(InteractiveComponent.class) != null){
            entity.add(new ShaderComponent(ShaderType.OUTLINE)); //TODO: add yellow outline
        }

        return entity;
    }

    public static Entity makeChest(int x, int y){
        Entity entity = new Entity();
        entity.add(new PositionComponent(x, y));
        entity.add(new SpriteComponent(new Sprite(
                AssetManager.instance.getTexture("tiles/grassland_tiles.png"), 0, 284, 64, 32)));
        entity.add(new InteractiveComponent(new TransferAction()));
        entity.add(new InventoryComponent(1000));
        entity.add(new ImpassableComponent());

        return entity;
    }

    public static Entity makeEntity(MapObject object, TiledMap map){
        Entity entity = new Entity();

        Vector2 position = new Vector2((float)object.getProperties().get(X)/32 - 1, //items is misplaced a little
                (float)object.getProperties().get(Y)/32);
        entity.add(new PositionComponent(position));

        //make components from specific properties
        fillComponents(entity, object.getProperties());

        if(object instanceof TiledMapTileMapObject) {
            //make components from properties of typed tiles
            fillComponents(entity, ((TiledMapTileMapObject)object).getTile().getProperties());

            //extract texture from tile directly
            entity.add(new SpriteComponent(new Sprite(((TiledMapTileMapObject)object).getTextureRegion())));

            //TODO: think about it
            entity.add(new ShaderComponent(ShaderType.OUTLINE));
        }

        //fill inventory with specified items
        if (im.get(entity) != null && object.getProperties().containsKey(ITEMS)){
            fillWithItems(entity, ((String)object.getProperties().get(ITEMS)).split(","));
        }

        return entity;
    }

    private static void fillComponents(Entity entity, MapProperties properties){
        Iterator<String> iterator = properties.getKeys();
        while (iterator.hasNext()){
            String property = iterator.next();
            Component component = makeComponent(property, properties.get(property));
            if(component != null) {
                entity.add(component);
            }
        }
    }

    private static Component makeComponent(String componentName, Object rawComponentData){
        switch (componentName) {
            case NAME:
                return new NameComponent((String) rawComponentData);
            case ACTION:
                return new InteractiveComponent(makeAction((String) rawComponentData));
            case INVENTORY:
                return new InventoryComponent((int)rawComponentData);
            case ITEMS:
                return null;//items is not a component, but in the same property map
            default:
                System.out.println("Warning! Cannot create component \"" + componentName + "\"!");
                return null;
        }
    }

    private static Action makeAction(String actionName){
        switch (actionName) {
            case TRANSFER_ACTION:
                return new TransferAction();
            default:
                System.out.println("Warning! Cannot create action \"" + actionName + "\"!");
                return null;
        }
    }

    private static void fillWithItems(Entity entity, String[] itemIds){
        for (String itemId : itemIds) {
            try {
                GameModel.instance.inventorySystem.addItem(entity, ItemFactory.getItem(Integer.parseInt(itemId)));
            } catch (NumberFormatException e){
                System.out.println("Error! Wrong format of item id: " + itemId);
            }
        }
    }
}
