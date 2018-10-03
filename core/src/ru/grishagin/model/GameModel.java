package ru.grishagin.model;

import com.badlogic.ashley.core.Engine;
import ru.grishagin.entities.Chest;
import ru.grishagin.entities.Player;
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
    }

    private void initBasicEntities(){
        engine.addEntity(new Player());
    }

    private void loadObjects(){
        engine.addEntity(new Chest());
    }


}
