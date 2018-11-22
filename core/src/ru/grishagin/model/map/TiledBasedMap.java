package ru.grishagin.model.map;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

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

    public MapObjects getObjects(){
        MapLayer layer = null;
        for (int i = 0; i < map.getLayers().size(); i++) {
            layer = map.getLayers().get(i);
            //find layer with objects. Logic as in @com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer.renderMapLayer
            if(!(layer instanceof MapGroupLayer) && !(layer instanceof TiledMapTileLayer)
                    && !(layer instanceof TiledMapImageLayer)){
                break;
            }
        }
        if (layer != null){
            return layer.getObjects();
        } else{
            return new MapObjects();
        }
    }
}
