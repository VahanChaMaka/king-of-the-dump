package ru.grishagin.model.map;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class TiledBasedMap {

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String TILE_WIDTH = "tilewidth";
    private static final String TILE_HEIGHT = "tileheight";

    private TiledMap map;

    public TiledBasedMap(TiledMap map) {
        this.map = map;
    }

    public TiledMap getMap() {
        return map;
    }

    public int getxSize() {
        return (Integer) map.getProperties().get(WIDTH);
    }

    public int getySize() {
        return (Integer) map.getProperties().get(HEIGHT);
    }

    public boolean isGlobal() {
        return false;
    }
}
