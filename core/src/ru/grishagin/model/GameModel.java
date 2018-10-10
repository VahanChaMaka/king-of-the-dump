package ru.grishagin.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.entities.EntityFactory;
import ru.grishagin.entities.ItemFactory;
import ru.grishagin.model.map.Map;
import ru.grishagin.model.map.MapFactory;
import ru.grishagin.systems.InteractionSystem;
import ru.grishagin.systems.InventorySystem;
import ru.grishagin.systems.MovementSystem;

public class GameModel {
    public static final GameModel instance = new GameModel();

    public final Engine engine;
    public final InventorySystem inventorySystem = new InventorySystem();
    private Map currentMap;

    private GameModel(){
        engine = new Engine();
        initSystems();
        initBasicEntities();
        loadObjects();

        currentMap = MapFactory.loadMap();
    }

    private void initSystems(){
        engine.addSystem(inventorySystem);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new InteractionSystem());
    }

    private void initBasicEntities(){
        engine.addEntity(EntityFactory.makePlayer(1, 1));
    }

    private void loadObjects(){
        Entity chest = EntityFactory.makeChest(10, 10);
        inventorySystem.addItem(chest, ItemFactory.getItem(0));
        engine.addEntity(chest);
    }

    public Map getCurrentMap() {
        return currentMap;
    }
}
