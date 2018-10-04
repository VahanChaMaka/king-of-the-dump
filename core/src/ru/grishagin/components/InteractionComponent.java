package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class InteractionComponent implements Component {
    public Entity aim;

    public InteractionComponent(Entity aim) {
        this.aim = aim;
    }
}
