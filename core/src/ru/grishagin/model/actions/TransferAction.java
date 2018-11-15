package ru.grishagin.model.actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.utils.UIManager;

public class TransferAction implements Action{

    //exmpl: target is a chest source is a player
    @Override
    public void execute(Entity source, Entity target) {
        ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);

        //if player open transfer dialog
        if(source.getComponent(PlayerControlled.class) != null) {
            UIManager.instance.openTransferWindow(target, source);
        }
    }
}
