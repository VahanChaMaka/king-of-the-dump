package ru.grishagin.view;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.SpriteComponent;

import java.util.ArrayList;
import java.util.List;

public class ExtendedIsometricTiledMapRenderer extends IsometricTiledMapRenderer {
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private ImmutableArray<Entity> spriteEntities;

    public ExtendedIsometricTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public void setSpriteEntities(ImmutableArray<Entity> spriteEntities) {
        //TODO: consider some sorting
        this.spriteEntities = spriteEntities;
    }

    @Override
    public void renderObject(MapObject object) {
        if(object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
            //batch.draw(textureObject.getTextureRegion(), textureObject.getX(), textureObject.getY());
            boolean isoMode = true;
            Vector3 renderPosition = new Vector3();
            float x = textureObject.getX();
            float y = textureObject.getY();
            if(isoMode) {//Isometric view
                int posY = -(int) x;
                int posX = (int) y;
                renderPosition.x = (posX - posY);
                renderPosition.y = (posX + posY) / 2;
            } else {//non-isometric view
                renderPosition.x = x;
                renderPosition.y = y;
            }
            batch.draw(textureObject.getTextureRegion(), renderPosition.x, renderPosition.y);
        }
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    for (Entity entity : spriteEntities) {
                        if(currentLayer == sm.get(entity).renderingOrder){
                            drawSprite(sm.get(entity), pm.get(entity));
                        }
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        endRender();
    }

    private void drawSprite(SpriteComponent spriteComponent, PositionComponent position){
        Vector3 renderPosition = new Vector3();
        float x = position.x;
        float y = position.y;
        float posY = - x * 32;
        float posX =  y * 32;
        renderPosition.x = (posX - posY);
        renderPosition.y = (posX + posY) / 2;
        //spriteComponent.sprite.draw(this.getBatch());
        this.batch.draw(spriteComponent.sprite, renderPosition.x, renderPosition.y);
    }
}
