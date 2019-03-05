package ru.grishagin.model.actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageManager;
import ru.grishagin.components.ClosedComponent;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.components.LockedComponent;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.tags.ImpassableComponent;
import ru.grishagin.model.messages.MessageType;
import ru.grishagin.ui.toolbar.Console;
import ru.grishagin.utils.Logger;
import ru.grishagin.utils.UIManager;

public class OpenAction implements Action {
    private ComponentMapper<LockedComponent> lm = ComponentMapper.getFor(LockedComponent.class);
    private ComponentMapper<ClosedComponent> cm = ComponentMapper.getFor(ClosedComponent.class);

    @Override
    public void execute(Entity source, Entity target) {
        LockedComponent locked = lm.get(target);
        ClosedComponent closed = cm.get(target);

        if(closed != null){
            if(closed.isClosed){
                if(locked.isLocked){
                    UIManager.instance.printMessageInConsole("Заперто!");
                    return;
                }

                //target is a container
                if(target.getComponent(InventoryComponent.class) != null){
                    TransferAction transferAction = new TransferAction();
                    transferAction.execute(source, target);
                } else {// some kind of a door
                    target.remove(ImpassableComponent.class);
                }

                MessageManager.getInstance().dispatchMessage(MessageType.OPENED, target);
            } else {
                //not sure if it is acceptable to make all opened entites is impassible
                //containers are expected to be closed after UI closing
                target.add(new ImpassableComponent());

                MessageManager.getInstance().dispatchMessage(MessageType.CLOSED, target);
            }

            closed.isClosed = !closed.isClosed;
            Logger.info(target, " is " + closed.isClosed + " now");
        } else {
            Logger.warning(target, "Can't close/open it!");
        }
    }
}