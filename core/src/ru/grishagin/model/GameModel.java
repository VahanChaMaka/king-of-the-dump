package ru.grishagin.model;

import com.badlogic.ashley.core.Engine;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.entities.Chest;
import ru.grishagin.entities.Player;
import ru.grishagin.systems.InteractionSystem;
import ru.grishagin.systems.MovementSystem;
import ru.grishagin.systems.RenderingSystem;

public class GameModel {
    public static final GameModel instance = new GameModel();

    public final Engine engine;

    private GameModel(){
        engine = new Engine();
        initSystems();
        initBasicEntities();
        loadObjects();
    }

    private void initSystems(){
        engine.addSystem(new MovementSystem());
        engine.addSystem(new InteractionSystem());
    }

    private void initBasicEntities(){
        engine.addEntity(new Player());
    }

    private void loadObjects(){
        engine.addEntity(new Chest());

        Chest ch2 = new Chest();
        ch2.getComponent(PositionComponent.class).x = 7;
        ch2.getComponent(PositionComponent.class).y = 12;
        engine.addEntity(ch2);

        Chest ch3 = new Chest();
        ch3.getComponent(PositionComponent.class).x = 8;
        ch3.getComponent(PositionComponent.class).y = 11;
        engine.addEntity(ch3);

        Chest ch4 = new Chest();
        ch4.getComponent(PositionComponent.class).x = 9;
        ch4.getComponent(PositionComponent.class).y = 11;
        engine.addEntity(ch4);
    }


}
