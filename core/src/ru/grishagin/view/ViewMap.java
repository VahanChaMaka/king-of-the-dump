package ru.grishagin.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import ru.grishagin.model.map.Map;
import ru.grishagin.model.map.MapCell;
import ru.grishagin.utils.AssetManager;

public class ViewMap {
    public final static int TILE_X_SIZE = 64;
    public final static int TILE_Y_SIZE = 32;

    private Map innerMap;
    private TiledMap map;
    private TiledMapRenderer renderer;
    private Texture tiles;

    private Batch batch;

    public ViewMap(Map innerMap, Batch batch){
        this.innerMap = innerMap;
        this.batch = batch;
        generateIso();
        renderer = new IsometricTiledMapRenderer(map, batch);
    }

    private void generateIso(){
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(innerMap.getxSize(), innerMap.getySize(), TILE_X_SIZE, TILE_Y_SIZE);
        TiledMapTileLayer perimeter = new TiledMapTileLayer(innerMap.getxSize(), innerMap.getySize(), TILE_X_SIZE, TILE_Y_SIZE);

        for (int i = 0; i < innerMap.getxSize(); i++) {
            for (int j = 0; j < innerMap.getySize(); j++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                MapCell innerCell = innerMap.getCell(i, j);
                StaticTiledMapTile tile = new StaticTiledMapTile(AssetManager.instance.getTileTexture(innerCell.getTypeId()));
                cell.setTile(tile);

                layer.setCell(i, j, cell);
            }
        }
        layers.add(layer);
        layers.add(perimeter);
    }

    public void draw(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render();
    }
}

