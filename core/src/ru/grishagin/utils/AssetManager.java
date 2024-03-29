package ru.grishagin.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.grishagin.components.OrientationComponent;
import ru.grishagin.components.ShaderType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    public static final AssetManager instance = new AssetManager();

    private Map<String, Texture> textures = new HashMap<>();
    private Map<String, TextureRegion> regions = new HashMap<>();
    private Map<String, TextureAtlas> atlases = new HashMap<>();
    private Map<String, Skin> skins = new HashMap<>();
    private Map<ShaderType, ShaderProgram> shaders = new HashMap<>();
    //TextureAtlas tilesAtlas = new TextureAtlas("tiles/tileset.atlas");

    private static final String PNG = ".png";

    private static String TILES_ATLAS = "tiles/tileset";
    private static String ICONS_ATLAS = "ui/icons/icons";
    private static String DAYR_UI = "ui/DayR/DayRSkin";

    //properties files
    public static final String NPC = "properties/npc.json";
    public static final String ITEMS = "properties/items.json";

    public static final String UI_BACKGROUND = "ui/old_paper.jpg";

    //entities states
    public static final String ATTACK_1 = "a1";
    public static final String DEAD = "di";
    public static final String WALKING = "mv";
    public static final String IDLE = "id";

    //equipped weapon
    public static final String KNIFE = "k";
    public static final String PISTOL = "p";

    //some constants
    private static final int FRAME_WIDTH = 144; //one and half tile width
    private static final int FRAME_HEIGHT = 144;

    public TextureRegion getTileTexture(int id) {
        TextureRegion region = null;
        TextureAtlas.AtlasRegion atlasRegion = getAtlas(TILES_ATLAS).findRegion(String.valueOf(id));
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
                atlasRegion = getAtlas(TILES_ATLAS).findRegion(String.valueOf("1"));
                region = new TextureRegion(atlasRegion.getTexture(), atlasRegion.getRegionX(), atlasRegion.getRegionY(),
                        atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight());
            }
        }

        return region;
    }

    public Texture getTexture(String name){
        if(textures.containsKey(name)){
            return textures.get(name);
        } else { //get texture by filename including path
            Texture newTexture = new Texture(name);
            textures.put(name, newTexture);
            return newTexture;
        }
    }

    public TextureRegion getUITexture(String name){
        if (regions.containsKey(name)) {
            return regions.get(name);
        } else{
            TextureRegion region = null;
            //try to find texture in atlas
            TextureAtlas.AtlasRegion atlasRegion = getAtlas(DAYR_UI).findRegion(name);
            if(atlasRegion != null){
                region = new TextureRegion(atlasRegion.getTexture(), atlasRegion.getRegionX(), atlasRegion.getRegionY(),
                        atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight());
            }
            //if texture was not found, try to load from file
            if(region == null) {
                //TODO: refactor to load texture only once and use textures map
                Texture texture = new Texture(name); //TODO: image can ba placed in different folders
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);//set filter to smooth scaling
                region = new TextureRegion(texture);
                regions.put(name, region);
            }
            return region;
        }
    }

    public TextureRegion getIcon(int id){
        TextureRegion region = null;
        TextureAtlas.AtlasRegion atlasRegion = getAtlas(ICONS_ATLAS).findRegion(String.valueOf(id));
        if(atlasRegion != null){
            //probable performance problems, consider caching
            region = new TextureRegion(atlasRegion.getTexture(), atlasRegion.getRegionX(), atlasRegion.getRegionY(),
                    atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight());
        } else {
            //try to load directly from file
            try {
                region = new TextureRegion(getTexture("icons/" + String.valueOf(id) + ".png"));
            } catch (Exception e){
                System.out.println("Warning! There is no icon with id " + id);
            }

            /*if(region == null) {
                atlasRegion = getAtlas(ICONS_ATLAS).findRegion(String.valueOf("1"));
                region = new TextureRegion(atlasRegion.getTexture(), atlasRegion.getRegionX(), atlasRegion.getRegionY(),
                        atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight());
            }*/
        }

        return region;
    }

    public Texture getNPCImage(int id){
        return getTexture("npc/" + id + "/sprite.png");
    }

    public Texture getNPCImage(int id, String state){
        switch (state){
            case DEAD:
                return getTexture("npc/" + id + "/" + DEAD + PNG);
            default:
                Logger.warning("There is no image for state '" + state + "' for " + id);
                return getNPCImage(id);
        }
    }

    public TextureRegion[] getNPCAnimation(int id, String state,
                                           String equippedWeapon,
                                           OrientationComponent.Orientation orientation){
        var pathBuilder = new StringBuilder("npc/");
        pathBuilder.append(id)
                .append("/")
                .append(state);
        if(equippedWeapon != null) {
            pathBuilder.append("_")
                    .append(equippedWeapon);
        }
        pathBuilder.append(PNG);

        Texture texture;
        try {
            texture = getTexture(pathBuilder.toString());
        } catch (GdxRuntimeException e) {
            Logger.error(e);
            //set idle as fallback texture
            texture = getTexture("npc/" + id + "/" + IDLE + PNG);
        }
        TextureRegion[][] frames = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);
        return switch (orientation) {
            case N -> frames[0];
            case NE -> frames[1];
            case E -> frames[2];
            case SE -> frames[3];
            case S -> frames[4];
            case SW -> frames[5];
            case W -> frames[6];
            case NW -> frames[7];
            default -> //wtf
                    frames[0];
        };
    }

    private TextureAtlas getAtlas(String name){
        if (atlases.containsKey(name)){
            return atlases.get(name);
        } else {
            TextureAtlas atlas = new TextureAtlas(name + ".atlas");
            atlases.put(name, atlas);
            return atlas;
        }
    }

    public Map<String, Map<String, Object>> readFromJson(String jsonName){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
            return mapper.readValue(Gdx.files.internal(jsonName).read(), HashMap.class);
        } catch (IOException exception){
            System.out.println("Error while reading " + jsonName + "!");
            throw new RuntimeException(exception);
        }
    }

    //skin name should be the same as texture atlas
    public Skin getSkin(String name){
        if (skins.containsKey(name)){
            return skins.get(name);
        } else {
            Skin skin = new Skin();
            BitmapFont font = FontGenerator.generate();
            skin.add("imperial12", font);
            skin.addRegions(getAtlas(name));
            skin.load(Gdx.files.internal(DAYR_UI + ".json"));
            skins.put(name, skin);
            return skin;
        }
    }

    public Skin getDefaultSkin(){
        return getSkin(DAYR_UI);
    }

    public ShaderProgram getShader(ShaderType shaderType){
        if(shaders.containsKey(shaderType)){
            return shaders.get(shaderType);
        } else {

            //on this point only default vertex shader is required
            String vertexShaderPath = Gdx.files.internal("shaders/defaultVertexShader.glsl").readString();

            String fragmentShaderPath = null;
            switch (shaderType){
                case OUTLINE:
                    fragmentShaderPath = Gdx.files.internal("shaders/outlineFragmentShader.glsl").readString();
                    break;
                default:
                    System.out.println("ERROR! Cannot load shader with name \"" + shaderType + "\"!");
                    return null;
            }

            ShaderProgram shader = new ShaderProgram(vertexShaderPath, fragmentShaderPath);
            if (!shader.isCompiled()){
                System.out.println("Error! Cannot compile shader with name \"" + shaderType + "\"!");
                System.out.println(shader.getLog());
                return null;
            } else {
                shaders.put(shaderType, shader);
                return shader;
            }
        }
    }

    public Sound getSound(String soundName){
        return Gdx.audio.newSound(Gdx.files.internal("sound/" + soundName));
    }

    private AssetManager(){
    }
}
