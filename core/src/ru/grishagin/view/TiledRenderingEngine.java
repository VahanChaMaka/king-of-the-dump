package ru.grishagin.view;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.grishagin.systems.DebugSystem;

public class TiledRenderingEngine{

    private TiledMap map;
    private Engine engine;
    private ExtendedIsometricTiledMapRenderer renderer;

    public TiledRenderingEngine(TiledMap map, Engine engine) {
        this.map = map;
        renderer = new ExtendedIsometricTiledMapRenderer(map, engine);
        this.engine = engine;

        DebugSystem drawer = engine.getSystem(DebugSystem.class);
        drawer.drawGrid();
    }

    public int getHeight() {
        //System.out.println("height: " + (Integer)map.getProperties().get("height"));
        return (Integer)map.getProperties().get("height") * (Integer)map.getProperties().get("tileheight");
    }

    public int getWidth() {
        //System.out.println("width: " + (Integer)map.getProperties().get("width"));
        return (Integer)map.getProperties().get("width") * (Integer)map.getProperties().get("tilewidth");
    }

    public boolean isOrtho() {
        return false;
    }

    public int getTileWidth(){
        return (int)map.getProperties().get("tilewidth");
    }

    public int getTileHeight(){
        return (int)map.getProperties().get("tileheight");
    }

    public void draw(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render();
    }
}
