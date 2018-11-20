package ru.grishagin.view;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.PositionComponent;

import java.util.Comparator;

@Deprecated
public class RenderComparator implements Comparator<Entity> {
    private ComponentMapper<PositionComponent> pm;

    public RenderComparator(){
        pm = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    public int compare(Entity entityA, Entity entityB) {
        float ax = pm.get(entityA).x;
        float ay = pm.get(entityA).y;
        float bx = pm.get(entityB).x;
        float by = pm.get(entityB).y;

        if(ax > bx){
            return 1;
        } else if(ax < bx){
            return -1;
        } else {
            if(ay > by){
                return -1;
            } else {
                return 1;
            }
        }
    }
}
