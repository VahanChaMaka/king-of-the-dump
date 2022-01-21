package ru.grishagin.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.entities.EntityFactory;
import ru.grishagin.model.map.MapFactory;
import ru.grishagin.model.map.TiledBasedMap;
import ru.grishagin.model.messages.MessageType;
import ru.grishagin.systems.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GameModel {
    public static final GameModel instance = new GameModel();

    public final Calendar date;
    public final Engine engine;
    public final InventorySystem inventorySystem = new InventorySystem(); //direct access to the system from different places
    public final MovementSystem movementSystem = new MovementSystem(); //same system for all maps. Call init() to change map
    private TiledBasedMap currentMap;

    private GameModel(){
        date = new GregorianCalendar(2030, 5, 3, 19, 32, 18);

        engine = new Engine();
        currentMap = MapFactory.loadMap();
        initSystems();
        initBasicEntities();
    }

    private void initSystems(){
        engine.addSystem(inventorySystem);

        engine.addSystem(movementSystem);
        movementSystem.setMap(currentMap);
        MessageManager.getInstance().addListener(movementSystem, MessageType.CLOSED);
        MessageManager.getInstance().addListener(movementSystem, MessageType.OPENED);

        engine.addSystem(new InteractionSystem());
        engine.addSystem(new CombatSystem());
        engine.addSystem(new AISystem());

        AnimationSystem animationSystem = new AnimationSystem();
        engine.addSystem(animationSystem);
        MessageManager.getInstance().addListener(animationSystem, MessageType.DEATH); //subscribe on death messages
        MessageManager.getInstance().addListener(animationSystem, MessageType.CLOSED);
        MessageManager.getInstance().addListener(animationSystem, MessageType.OPENED);
        MessageManager.getInstance().addListener(animationSystem, MessageType.ORIENTATION_CHANGE);
        MessageManager.getInstance().addListener(animationSystem, MessageType.STOP_MVMNT);
        MessageManager.getInstance().addListener(animationSystem, MessageType.START_MVMNT);

        ShaderSystem shaderSystem = new ShaderSystem();
        engine.addSystem(shaderSystem);
        MessageManager.getInstance().addListener(shaderSystem, MessageType.DEATH);

        SoundSystem soundSystem = new SoundSystem();
        MessageManager.getInstance().addListeners(soundSystem, MessageType.DEATH,
                MessageType.ATTACK, MessageType.OPENED, MessageType.CLOSED);
        engine.addSystem(soundSystem);

        engine.addSystem(new DebugSystem());
    }

    private void initBasicEntities(){

    }

    public void loadObjects(){
        /*Entity chest = EntityFactory.makeChest(1, 2);
        inventorySystem.addItem(chest, ItemFactory.getItem(0));
        inventorySystem.addItem(chest, ItemFactory.getItem(1001));
        engine.addEntity(chest);*/

        //engine.addEntity(EntityFactory.makeNPC());
        //engine.addEntity(EntityFactory.makeNPC(0, 6, 5));
        //engine.addEntity(EntityFactory.makeNPC(100, 15, 7));

        for (MapObject object : currentMap.getObjects()) {
            engine.addEntity(EntityFactory.makeEntity(object, currentMap.getMap()));
        }

        //spawn NPCs and player
        for (RectangleMapObject spawner: currentMap.getSpawners()){
            engine.addEntity(EntityFactory.spawn(spawner, currentMap.getMap()));
        }
    }

    public TiledBasedMap getCurrentMap() {
        return currentMap;
    }

    public Entity getPlayer(){
        return engine.getEntitiesFor(Family.all(PlayerControlled.class).get()).first();
    }
}
