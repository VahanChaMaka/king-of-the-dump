package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class AttackTargetComponent implements Component {
    public Entity target;

    public AttackTargetComponent(Entity target) {
        this.target = target;
    }
}
