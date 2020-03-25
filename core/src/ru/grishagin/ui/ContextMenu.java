package ru.grishagin.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import ru.grishagin.ui.menu.MenuAction;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.UIManager;

import java.util.List;

import static ru.grishagin.model.messages.MessageType.UI_UPDATE;

public class ContextMenu extends Dialog {

    private static final int MENU_WIDTH = 50;
    private static final int MENU_HEIGHT = 150;

    private static final int BUTTON_WIDTH = 130;
    private static final int BUTTON_HEIGHT = 30;

    public ContextMenu(){
        super("", AssetManager.instance.getDefaultSkin());

        //background image is the shared resource, it could lead to some unexpected behavior of the other UI components
        getBackground().setMinWidth(MENU_WIDTH);
        getBackground().setMinHeight(MENU_HEIGHT);

        getButtonTable().defaults().height(BUTTON_HEIGHT);
        getButtonTable().defaults().width(BUTTON_WIDTH);
        show(UIManager.instance.getStage(UIManager.MAIN_STAGE));

        setWidth(BUTTON_WIDTH);

        addListener(getCloseListener());
    }

    //close dialog on click outside or on ESC/BACKSPACE buttons
    private InputListener getCloseListener(){
        return new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (event.getKeyCode() == Input.Keys.BACK || event.getKeyCode() == Input.Keys.ESCAPE) {
                    hide();
                    event.cancel();
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (x < 0 || x > getWidth() || y < 0 || y > getHeight()){
                    hide();
                    event.cancel();
                    return true;
                }
                return false;
            }
        };
    }

    public void setActions(List<MenuAction> actions){
        for (MenuAction action : actions) {
            button(action.getTitle(), action.getAction());
        }

        setHeight((BUTTON_HEIGHT + getButtonTable().defaults().getSpaceBottom()) * actions.size());
    }

    @Override
    public Dialog button(Button button, Object object) {
        if(!(object instanceof Runnable)){
            throw new IllegalArgumentException("Only Runnable can be result!");
        }

        getButtonTable().row();
        getButtonTable().add(button);
        setObject(button, object);
        return this;
    }

    @Override
    protected void result(Object result) {
        //only runnables should be added as result on buttons
        Runnable action = (Runnable) result;
        action.run();

        MessageManager.getInstance().dispatchMessage(UI_UPDATE);
    }

    @Override
    public void hide() {
        super.hide();

        //update UI to return pressed buttons to initial style
        MessageManager.getInstance().dispatchMessage(UI_UPDATE);
    }
}
