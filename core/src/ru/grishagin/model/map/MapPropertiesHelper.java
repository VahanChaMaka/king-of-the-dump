package ru.grishagin.model.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapPropertiesHelper {

    private static final String IMPASSABLE = "impassable";

    public static boolean isWalkable(TiledBasedMap map, int x, int y){
        for (MapLayer layer : map.getMap().getLayers()) {
            if(layer instanceof TiledMapTileLayer){
                if(((TiledMapTileLayer)layer).getCell(x, y) != null) {
                    MapProperties properties = ((TiledMapTileLayer) layer).getCell(x, y).getTile().getProperties();
                    if (properties.containsKey(IMPASSABLE) && (boolean) properties.get(IMPASSABLE)) {
                        return false;
                    }
                }
            }

            //TODO: add check for objects
        }
        return true;
    }
}
