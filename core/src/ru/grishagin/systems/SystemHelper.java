package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.PositionComponent;

public class SystemHelper {
    private static ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    //returns distance between two entities. Result is always non-negative
    public static float getDistance(Entity one, Entity another){
        float deltaX = pm.get(one).x - pm.get(another).x;
        float deltaY = pm.get(one).y - pm.get(another).y;
        return (float)Math.abs(Math.sqrt(deltaX*deltaX + deltaY*deltaY));
    }
}
