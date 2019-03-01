package ru.grishagin.model.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;

import static ru.grishagin.entities.EntityFactory.X;
import static ru.grishagin.entities.EntityFactory.Y;

public class MapPropertiesHelper {

    private static final String IMPASSABLE = "impassable";

    //only static objects are checked
    public static boolean isWalkable(TiledBasedMap map, int x, int y){
        for (MapLayer layer : map.getMap().getLayers()) {

            //iterate over objects in the layer
            for (MapObject object: layer.getObjects()){
                //check position
                Vector2 position = convertObjectMapCoordsToInternal(object);
                if((int)position.x == x && (int)position.y == y){
                    //check class  of the object
                    if(object instanceof TiledMapTileMapObject){
                        //check property IMPASSABLE
                        if (((TiledMapTileMapObject) object).getTile().getProperties().containsKey(IMPASSABLE)
                                && (boolean) ((TiledMapTileMapObject) object).getTile().getProperties().get(IMPASSABLE)) {
                            return false;
                        }
                    }
                }
            }

            //iterate other tiles
            if(layer instanceof TiledMapTileLayer){
                if(((TiledMapTileLayer)layer).getCell(x, y) != null) {
                    MapProperties properties = ((TiledMapTileLayer) layer).getCell(x, y).getTile().getProperties();
                    if(properties.getValues().hasNext()){
                        System.out.println();
                    }
                    if (properties.containsKey(IMPASSABLE) && (boolean) properties.get(IMPASSABLE)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    //convert pixel coord from Tiled into cell-coordinates
    public static Vector2 convertObjectMapCoordsToInternal(MapObject object){
        Vector2 internalPosition = new Vector2((float)object.getProperties().get(X)/32 - 1, //items is misplaced a little
                (float)object.getProperties().get(Y)/32);
        return internalPosition;
    }
}
