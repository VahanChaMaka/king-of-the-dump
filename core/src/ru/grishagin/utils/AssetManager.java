package ru.grishagin.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    public static final AssetManager instance = new AssetManager();

    private Map<String, Texture> textures = new HashMap<>();

    public TextureRegion getTileTexture(int id){
        Texture tileSet = getTexture("tiles/grassland_tiles.png");
        if(id == 0) {
            return new TextureRegion(tileSet, 64, 32);
        } else if(id == 1) {
            return new TextureRegion(tileSet, 64, 32,64, 32);
        } else {
            return new TextureRegion(tileSet, 128, 32,64, 32);
        }
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
