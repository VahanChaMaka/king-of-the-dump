package ru.grishagin.view;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.utils.Logger;

import java.util.Comparator;

public class DepthComparator<T extends Entity> implements Comparator<T> {
    private static ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    @Override
    public int compare(T o1, T o2) {
        PositionComponent o1Position = pm.get(o1);
        PositionComponent o2Position = pm.get(o2);
        if(o1Position != null && o2Position != null){
            if(o1Position.x > o2Position.x) {
                return 1;
            } else if(o1Position.x < o2Position.x){
                return -1;
            } else{
                return 0;
            }
        } else {
            Logger.warning(o1, "Trying to sort entities without position!");
            Logger.warning(o2, "");

            return 0;
        }
    }
}
