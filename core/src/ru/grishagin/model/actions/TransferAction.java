package ru.grishagin.model.actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.components.NameComponent;

public class TransferAction implements Action{
    @Override
    public void execute(Entity source, Entity target) {
        ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);
        //TODO: call ui manager transfer window to move items


        System.out.println("chest is opened");
        System.out.println(im.get(target).items.get(0).getComponent(NameComponent.class).name);
    }
}
