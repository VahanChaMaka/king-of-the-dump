package ru.grishagin.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    public static final AssetManager instance = new AssetManager();

    private Map<String, Texture> textures = new HashMap<>();
    TextureAtlas tilesAtlas = new TextureAtlas("tiles/tileset.atlas");

    public TextureRegion getTileTexture(int id) {
        TextureRegion region = null;
        TextureAtlas.AtlasRegion atlasRegion = tilesAtlas.findRegion(String.valueOf(id));
        if(atlasRegion != null){
            //probable performance problems, consider caching
            region = new TextureRegion(atlasRegion.getTexture(), atlasRegion.getRegionX(), atlasRegion.getRegionY(),
                    atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight());
        } else {
            //try to load directly from file
            try {
                region = new TextureRegion(getTexture("tiles/" + String.valueOf(id) + ".png"));
            } catch (Exception e){
                System.out.println("Warning! There is no tile with id " + id);
            }

            if(region == null) {
                atlasRegion = tilesAtlas.findRegion(String.valueOf("1"));
                region = new TextureRegion(atlasRegion.getTexture(), atlasRegion.getRegionX(), atlasRegion.getRegionY(),
                        atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight());
            }
        }

        return region;
    }

    public Texture getTexture(String name){
        if(textures.containsKey(name)){
            return textures.get(name);
        } else {
            Texture newTexture = new Texture(name);
            textures.put(name, newTexture);
            return newTexture;
        }
    }

    public Map<String, Map<String, Object>> readFromJson(String jsonName){
        try {
            return new ObjectMapper().readValue(Gdx.files.internal(jsonName).file(), HashMap.class);
        } catch (IOException exception){
            System.out.println("Error while reading " + jsonName + "!");
            throw new RuntimeException(exception);
        }
    }


    private AssetManager(){
    }
}
