package ru.grishagin.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.entities.EntityFactory;
import ru.grishagin.entities.ItemFactory;
import ru.grishagin.model.map.Map;
import ru.grishagin.model.map.MapFactory;
import ru.grishagin.systems.InteractionSystem;
import ru.grishagin.systems.InventorySystem;
import ru.grishagin.systems.MovementSystem;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GameModel {
    public static final GameModel instance = new GameModel();

    public final Calendar date;
    public final Engine engine;
    public final InventorySystem inventorySystem = new InventorySystem();
    private Map currentMap;

    private GameModel(){
        date = new GregorianCalendar(2030, 5, 3, 19, 32, 18);

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
        Entity chest = EntityFactory.makeChest(1, 2);
        inventorySystem.addItem(chest, ItemFactory.getItem(0));
        inventorySystem.addItem(chest, ItemFactory.getItem(1001));
        engine.addEntity(chest);
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public Entity getPlayer(){
        return engine.getEntitiesFor(Family.all(PlayerControlled.class).get()).first();
    }
}
