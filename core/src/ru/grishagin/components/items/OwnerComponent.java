package ru.grishagin.components.items;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class OwnerComponent implements Component {
    public final Entity owner;

    public OwnerComponent(Entity owner) {
        this.owner = owner;
    }
}
