package ru.grishagin.model;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ru.grishagin.components.*;
import ru.grishagin.components.tags.HostileTag;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.view.TiledRenderingEngine;

public class InputHandler {
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<ShaderComponent> shm = ComponentMapper.getFor(ShaderComponent.class);
    private ComponentMapper<InteractiveComponent> im = ComponentMapper.getFor(InteractiveComponent.class);
    private ComponentMapper<HostileTag> hm = ComponentMapper.getFor(HostileTag.class);
    private ComponentMapper<AttackTargetComponent> atm = ComponentMapper.getFor(AttackTargetComponent.class);

    private Engine engine;
    private TiledRenderingEngine map;

    public InputHandler(Engine engine, TiledRenderingEngine map) {
        this.engine = engine;
        this.map = map;
    }

    //X and Y are world-coords
    public void onClick(float x, float y){
        Entity player = engine.getEntitiesFor(Family.all(PlayerControlled.class).get()).first();

        //clear current movement info
        player.remove(DestinationComponent.class);
        VelocityComponent currentVelocity = player.getComponent(VelocityComponent.class);
        currentVelocity.x = 0;
        currentVelocity.y = 0;

        boolean isSomeActionHappens = false;//flag to indicate there is no interactable object on this coords
        for (Entity entity : engine.getEntitiesFor(Family.all(SpriteComponent.class).get())) {
            SpriteComponent spriteComponent = sm.get(entity);
            if(withinSprite(spriteComponent.sprite, (int)x, (int)y)){
                if(im.get(entity) != null){
                    player.add(new InteractionComponent(entity));
                    isSomeActionHappens = true;
                } else if(hm.get(entity) != null){//if clicked target is an enemy, attack it
                    player.add(new AttackTargetComponent(entity));
                    isSomeActionHappens = true;
                }
            } else {
                //do nothing
            }
        }

        //is no interaction happens, move player
        //convert from ortho world-coords to iso-game coords
        if(!isSomeActionHappens){

            //if player previously attacked someone, cancel it
            if(atm.get(player) != null){
                player.remove(AttackTargetComponent.class);
            }

            y = y - map.getTileHeight()/2; //world-coord and iso-coord center are shifted a little

            float _x, _y;
            float tileX, tileY;

            _x = (x + 2 * y) / 2;
            _y = (2 * y - x) / 2;

            //yes, tileX = -_y/32. Screen and game coordinates are twisted
            tileX = -_y/map.getTileHeight();
            tileY = _x/map.getTileHeight();
            player.add(new DestinationComponent((int)tileX, (int)tileY));
        }
    }

    //X and Y are world-coords
    public void onHover(float x, float y){
        for (Entity entity : engine.getEntitiesFor(Family.all(SpriteComponent.class).get())) {
            if(shm.get(entity) != null) { //not all entities with sprites have attached shader
                SpriteComponent spriteComponent = sm.get(entity);
                if (withinSprite(spriteComponent.sprite, (int) x, (int) y)) {
                    shm.get(entity).isActive = true;
                } else {
                    //turn off outline on other sprites
                    shm.get(entity).isActive = false;
                }
            }
        }
    }
    
    private Entity getEntity(float x, float y){
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(PositionComponent.class, InteractiveComponent.class).get());
        ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
        for (Entity entity : entities) {
            PositionComponent position = pm.get(entity);
            if((int)position.x == (int)x && (int)position.y == (int)y){
                return entity;
            }
        }

        return null;
    }

    private void clearInteractions(){
        ComponentMapper<InteractiveComponent> im = ComponentMapper.getFor(InteractiveComponent.class);
        for (Entity entity : engine.getEntitiesFor(Family.all(InteractiveComponent.class).get())) {

        }
    }

    private boolean withinSprite(Sprite sprite, int x, int y){
        if(x > sprite.getX() && x < sprite.getX() + sprite.getRegionWidth()
                && y > sprite.getY() && y < sprite.getY() + sprite.getRegionHeight()) {

            //get local sprite coordinates
            int spriteLocalX = (int) (x - sprite.getX());
            //world coordinates and sprite are inverted
            int spriteLocalY = (int) (sprite.getHeight() - (y - sprite.getY()));

            //convert to texture coordinates
            int textureLocalX = sprite.getRegionX() + spriteLocalX;
            int textureLocalY = sprite.getRegionY() + spriteLocalY;

            //don't know what happens here))
            if (!sprite.getTexture().getTextureData().isPrepared()) {
                sprite.getTexture().getTextureData().prepare();
            }
            Pixmap pixmap = sprite.getTexture().getTextureData().consumePixmap();

            //check if pixen is not transparent
            if(new Color(pixmap.getPixel(textureLocalX, textureLocalY)).a > 0.5){
                return true;
            }
        }

        //if not in sprite or pixel is transparent
        return false;
    }
}
