package ru.grishagin.model.actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.InventoryComponent;

public class TransferAction implements Action{
    @Override
    public void execute(Entity source, Entity target) {
        ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);
        //TODO: call ui manager transfer window to move items
        System.out.println("chest is opened");
    }
}
