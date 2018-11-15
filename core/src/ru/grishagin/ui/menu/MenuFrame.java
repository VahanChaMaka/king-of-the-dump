package ru.grishagin.ui.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.grishagin.ui.toolbar.BottomToolbar;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.UIManager;

public abstract class MenuFrame extends Table {
    private static final String CLOSE_BUTTON = "ui/icon_close.png";

    private Container<Actor> contentContainer = new Container<>();

    public MenuFrame(){
        setBackground(new TextureRegionDrawable(AssetManager.instance.getUITexture(AssetManager.UI_BACKGROUND)));

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(AssetManager.instance.getUITexture(CLOSE_BUTTON)));
        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                UIManager.instance.setMenuOpened(false);//set false to allow input events go to the map and hide menu
                ((BottomToolbar)UIManager.instance.getPanel(UIManager.BOTTOM_TOOLBAR)).checkButton(BottomToolbar.UNCHECK_ALL);
            }
        });
        add(closeButton).top().right();
        row();
        add(contentContainer).fill().expand();

        UIManager.instance.putPanel(UIManager.CURRENT_INVENTORY_MENU, this);
    }

    protected void setupContent(){
        contentContainer.setActor(createContent());
    }

    protected abstract Actor createContent();
}
