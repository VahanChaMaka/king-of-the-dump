package ru.grishagin.model.map;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.grishagin.utils.Logger;

import static ru.grishagin.entities.EntityFactory.X;
import static ru.grishagin.entities.EntityFactory.Y;

public class TiledBasedMap {

    public static final String ROOF_LAYER = "roof";

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    public static final String TILE_WIDTH = "tilewidth";
    public static final String TILE_HEIGHT = "tileheight";

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

    private MapLayer getLayer(String layerName){
        for (MapLayer layer : map.getLayers()) {
            if(layerName.equals(layer.getName())){
                return layer;
            }
        }
        Logger.warning("There is no layer with name " + layerName);
        return null;
    }

    public void setLayerVisibility(String layerName, boolean isVisible){
        MapLayer layer = getLayer(layerName);
        if(layer != null){
            layer.setVisible(isVisible);
        }
    }

    //if specified layer has something in x y
    public boolean hasObject(float x, float y, String layerName){
        MapLayer layer = getLayer(layerName);
        if(layer != null){
            if(layer instanceof TiledMapTileLayer){
                TiledMapTileLayer.Cell cell = ((TiledMapTileLayer)layer).getCell((int)x, (int)y);
                if(cell != null){
                    return true;
                }
            }

            for (MapObject object : layer.getObjects()) {
                if((float)object.getProperties().get(X) == x && (float)object.getProperties().get(Y) == y){
                    return true;
                }
            }
        }
        return false;
    }
}
