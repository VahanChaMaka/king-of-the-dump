package ru.grishagin.model.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.TileGIdComponent;
import ru.grishagin.utils.Logger;

import static ru.grishagin.entities.EntityFactory.X;
import static ru.grishagin.entities.EntityFactory.Y;
import static ru.grishagin.model.map.TiledBasedMap.TILE_HEIGHT;
import static ru.grishagin.model.map.TiledBasedMap.TILE_WIDTH;

public class TiledMapHelper {

    private static final String IMPASSABLE = "impassable";
    private static final String FIRST_GID = "firstgid";

    //only static objects are checked
    public static boolean isWalkable(TiledBasedMap map, int x, int y){
        for (MapLayer layer : map.getMap().getLayers()) {

            //iterate over objects in the layer
            for (MapObject object: layer.getObjects()){
                //check position
                Vector2 position = convertObjectMapCoordsToInternal(object, map.getMap());
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
    public static Vector2 convertObjectMapCoordsToInternal(MapObject object, TiledMap map){
        int tileHeight = (int)map.getProperties().get(TILE_HEIGHT);
        Vector2 internalPosition = new Vector2((float)object.getProperties().get(X)/tileHeight - 1, //items is misplaced a little
                (float)object.getProperties().get(Y)/tileHeight);
        return internalPosition;
    }

    //convert local id of the next state to global id and return texture region
    public static TextureRegion getStateTextureRegion(TiledMap map, int stateId, Entity entity){
        int gid = entity.getComponent(TileGIdComponent.class).gid;
        int tileSetFirstGid = getFirstGid(map, gid);

        return map.getTileSets().getTile(tileSetFirstGid + stateId).getTextureRegion();
    }

    private static int getFirstGid(TiledMap map, int tileGid){
        //find first tileset with startgid less or equal tileGid (tilesets order by startgid)
        TiledMapTileSet previousTileSet = map.getTileSets().getTileSet(0);
        for (TiledMapTileSet tileSet : map.getTileSets()) {
            if((int)tileSet.getProperties().get(FIRST_GID) > tileGid){
                return (int)previousTileSet.getProperties().get(FIRST_GID);
            }
            previousTileSet = tileSet;
        }
        return (int)previousTileSet.getProperties().get(FIRST_GID);
    }
}
