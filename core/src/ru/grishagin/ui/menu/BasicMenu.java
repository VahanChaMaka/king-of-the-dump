package ru.grishagin.ui.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.grishagin.ui.toolbar.BottomToolbar;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.UIManager;

/**
 * Created by Admin on 14.01.2018.
 */
public class BasicMenu extends Table {
    protected Table leftPanel;
    protected Table itemsFilterToolbar;
    protected Table rightPanel;
    protected Container rightContainer = new Container();

    public BasicMenu(){
        setBackground(new TextureRegionDrawable(AssetManager.instance.getUITexture(AssetManager.UI_BACKGROUND)));

        UIManager.instance.putPanel(UIManager.CURRENT_INVENTORY_MENU, this);

        debugAll();
        pad(5);
    }

    protected void createMainLayout(){
        leftPanel = createLeftPanel();
        add(leftPanel).fill().expand().pad(10);

        rightPanel = createRightPanel();
        add(rightPanel).fill().right().top().width(300).expandY();
    }

    protected Table createLeftPanel(){
        Table layout = new Table();

        layout.debugAll();
        layout.pad(3);

        itemsFilterToolbar = createItemsFilterToolbar();
        layout.add(itemsFilterToolbar).left().width(150).fill();

        return layout;
    }

    protected Table createItemsFilterToolbar(){
        Table toolbar = new Table();
        toolbar.debugAll();

        return toolbar;
    }

    protected Table createRightPanel(){
        Table itemInfoPanel = new Table();
        itemInfoPanel.debugAll();
        itemInfoPanel.pad(10);

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(AssetManager.instance.getUITexture(AssetManager.CLOSE_BUTTON)));
        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                UIManager.instance.setMenuOpened(false);//set false to allow input events go to the map and hide menu
                ((BottomToolbar)UIManager.instance.getPanel(UIManager.BOTTOM_TOOLBAR)).checkButton(BottomToolbar.UNCHECK_ALL);
            }
        });
        itemInfoPanel.add(closeButton).top().right();

        itemInfoPanel.row();
        itemInfoPanel.add(rightContainer).expand().fill();

        return itemInfoPanel;
    }

}
