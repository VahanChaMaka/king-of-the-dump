package ru.grishagin.model;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import ru.grishagin.components.DirectionComponent;
import ru.grishagin.components.InteractiveComponent;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.entities.Player;

public class ClickHandler {
    private Engine engine;

    public ClickHandler(Engine engine) {
        this.engine = engine;
    }

    //internal game coords already
    public void onClick(float x, float y){
        clearInteractions();

        Entity entity = getEntity(x, y);
        if(entity != null){
            //interact with item
            //HOW????
        } else { //move player
            Entity player = engine.getEntitiesFor(Family.all(PlayerControlled.class).get()).first();
            player.getComponent(DirectionComponent.class).x = x;
            player.getComponent(DirectionComponent.class).y = y;
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
            im.get(entity).isActive = false;
        }
    }
}
