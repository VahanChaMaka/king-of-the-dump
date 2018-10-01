package ru.grishagin.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    public static final AssetManager instance = new AssetManager();

    private Map<String, Texture> textures = new HashMap<>();

    public TextureRegion getTileTexture(int id){
        Texture tileSet = getTexture("tiles/grassland_tiles.png");
        return new TextureRegion(tileSet, 64, 32);
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


    private AssetManager(){
    }
}
