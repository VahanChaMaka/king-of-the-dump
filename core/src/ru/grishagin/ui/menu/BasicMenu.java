package ru.grishagin.ui.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.grishagin.ui.toolbar.BottomToolbar;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.UIManager;

/**
 * Created by Admin on 14.01.2018.
 */
public class BasicMenu extends MenuFrame {

    protected Table leftPanel;
    protected Table itemsFilterToolbar;
    protected Container<WidgetGroup> rightContainer = new Container<>();

    public BasicMenu(){
        debugAll();
        pad(5);
    }

    @Override
    protected Actor createContent() {
        return null;
    }

    protected void createMainLayout(){
        leftPanel = createLeftPanel();
        add(leftPanel).fill().expand().pad(10);

        rightContainer.setActor(createRightPanel());
        add(rightContainer).fill().right().top().width(300).expandY();
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

        itemInfoPanel.row();

        return itemInfoPanel;
    }

}
