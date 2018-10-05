package ru.grishagin.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.InventoryComponent;

public class InventorySystem extends IteratingSystem {

    public InventorySystem() {
        super(Family.all(InventoryComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //TODO: update items in each inventory
    }
}
