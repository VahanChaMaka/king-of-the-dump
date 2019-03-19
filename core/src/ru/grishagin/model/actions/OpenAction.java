package ru.grishagin.model.actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.*;
import ru.grishagin.components.tags.ImpassableComponent;
import ru.grishagin.model.GameModel;
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
                if(locked != null && locked.isLocked){
                    UIManager.instance.printMessageInConsole("Заперто!");
                    return;
                }

                //target is a container
                if(target.getComponent(InventoryComponent.class) != null){
                    TransferAction transferAction = new TransferAction();
                    transferAction.execute(source, target);
                }

                MessageManager.getInstance().dispatchMessage(MessageType.OPENED, target);
            } else {
                MessageManager.getInstance().dispatchMessage(MessageType.CLOSED, target);
            }

            closed.isClosed = !closed.isClosed;
            Logger.info(target, " is closed=" + closed.isClosed + " now");
        } else {
            Logger.warning(target, "Can't close/open it!");
        }
    }
}
