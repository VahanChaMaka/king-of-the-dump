package ru.grishagin.view;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.FloatingTextComponent;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.ShaderComponent;
import ru.grishagin.components.SpriteComponent;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.FontGenerator;
import ru.grishagin.utils.ShaderHelper;

import java.util.*;

import static ru.grishagin.model.map.TiledBasedMap.TILE_HEIGHT;

public class ExtendedIsometricTiledMapRenderer extends IsometricTiledMapRenderer {
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<ShaderComponent> shm = ComponentMapper.getFor(ShaderComponent.class);

    private DepthComparator<Entity> comparator;

    private Engine engine;

    public ExtendedIsometricTiledMapRenderer(TiledMap map, Engine engine) {
        super(map);
        this.engine = engine;
        comparator = new DepthComparator<>();
    }

    @Override
    public void render() {
        List<Entity> spriteEntities = Arrays.asList(
                engine.getEntitiesFor(Family.all(SpriteComponent.class).get())
                        .toArray(Entity.class));
        Collections.sort(spriteEntities, comparator);

        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                renderMapLayer(layer);
                currentLayer++;
                for (Entity entity : spriteEntities) {
                    if(currentLayer == sm.get(entity).renderingOrder){
                        drawSprite(sm.get(entity), pm.get(entity), shm.get(entity));
                    }
                }
            }
        }

        renderText();

        endRender();
    }

    private void drawSprite(SpriteComponent spriteComponent, PositionComponent position, ShaderComponent shaderComponent){
        Vector2 renderPosition = getRenderPosition(position);

        //FOR DEVELOPMENT ONLY!
        Sprite s = new Sprite(AssetManager.instance.getTexture("tiles/red.png"));
        s.setAlpha(0.5f);
        //batch.draw(s, renderPosition.x, renderPosition.y);
        ///

        batch.end();

        //TODO: setting the same shader every time can affect performance
        if(shaderComponent != null && shaderComponent.shaderType != null && shaderComponent.isActive) {
            //get shader with properties
            ShaderProgram shader = ShaderHelper.getShader(shaderComponent, spriteComponent);
            getBatch().setShader(shader);
        } else {
            //setup default shader
            batch.setShader(null);
        }

        spriteComponent.sprite.setPosition(renderPosition.x + spriteComponent.offset.x,
                renderPosition.y + spriteComponent.offset.y);

        batch.begin();

        this.batch.draw(spriteComponent.sprite, spriteComponent.sprite.getX(), spriteComponent.sprite.getY());
        /*this.batch.draw(spriteComponent.sprite, renderPosition.x + spriteComponent.offset.x,
                renderPosition.y + spriteComponent.offset.y);*/

        getBatch().setShader(null);
    }

    private void renderText(){
        BitmapFont font = FontGenerator.generate(8, Color.BLACK);
        ImmutableArray<Entity> textEntities = engine.getEntitiesFor(Family.all(FloatingTextComponent.class, PositionComponent.class).get());
        for (Entity text : textEntities) {
            FloatingTextComponent textComponent = text.getComponent(FloatingTextComponent.class);
            Vector2 renderPosition = getRenderPosition(text.getComponent(PositionComponent.class));
            renderPosition = renderPosition.add(textComponent.getOffset());
            font.draw(getBatch(), textComponent.getText(), renderPosition.x, renderPosition.y);
        }
    }

    private Vector2 getRenderPosition(PositionComponent position){
        int tileHeight = (int)map.getProperties().get(TILE_HEIGHT);
        Vector2 renderPosition = new Vector2();
        float x = position.x;
        float y = position.y;
        float posY = - x * tileHeight;
        float posX =  y * tileHeight;
        renderPosition.x = (posX - posY);
        renderPosition.y = (posX + posY) / 2;

        return renderPosition;
    }
}
