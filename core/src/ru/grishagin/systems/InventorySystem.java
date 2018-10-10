package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.InventoryComponent;

public class InventorySystem extends IteratingSystem {
    private ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);

    public InventorySystem() {
        super(Family.all(InventoryComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //TODO: update items in each inventory
    }

    public void addItem(Entity target, Entity item){
        InventoryComponent inventory = im.get(target);

        //TODO: check weight limit

        //TODO: stack items not add new instance
        inventory.items.add(item);
    }

    public void destroyItem(Entity target){

    }

    public void transferItem(Entity source, Entity target){

    }
}
