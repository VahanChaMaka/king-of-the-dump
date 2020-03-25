package ru.grishagin.ui.toolbar;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.model.GameModel;
import ru.grishagin.ui.CentralPanel;
import ru.grishagin.ui.ContextMenu;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.LayoutUtils;
import ru.grishagin.utils.Logger;
import ru.grishagin.utils.UIManager;
import ru.grishagin.ui.menu.*;

import java.util.HashMap;
import java.util.Map;

public class BottomToolbar extends Container {
    public static final String INVENTORY_BTN_NAME = "Инвентарь";
    public static final String PERS_BTN_NAME = "Персонаж";
    public static final String CRAFT_BTN_NAME = "Чертежи";
    public static final String DISCOVERY_BTN_NAME = "Местность";
    public static final String JOURNAL_BTN_NAME = "Журнал";
    public static final String UNCHECK_ALL = "UNCHECK_ALL";

    public static final int TOOLBAR_HEIGHT = 100;

    public static final String BTN_GROUP_BACKGROUND = "chat";

    Table layout = new Table();

    private Map<String, Button> buttons = new HashMap<>();

    public BottomToolbar(){
        StatPanel statPanel = new StatPanel();
        statPanel.addPersInfoButton(createPersInfoButton());
        layout.add(createConsole()).fill().width(280);
        layout.add(statPanel).fill().expand();
        layout.add(createToolbarButtonGroup()).right().width(250);

        setBackground(new TextureRegionDrawable(AssetManager.instance.getUITexture(AssetManager.UI_BACKGROUND)));

        setActor(layout);
        align(Align.right);
        fill();
        UIManager.instance.putPanel(UIManager.BOTTOM_TOOLBAR, this);
    }

    private Actor createToolbarButtonGroup(){
        Table layout = new Table();
        layout.defaults().pad(5);
        LayoutUtils.applyButtonSize(layout.defaults());
        layout.setBackground(new TextureRegionDrawable(AssetManager.instance.getUITexture(BTN_GROUP_BACKGROUND)));
        //layout.debugAll();

        layout.add(createInventoryButton());
        layout.add(createDiscoveryButton());
        layout.row();
        layout.add(createJournalButton());
        layout.add(createCraftButton());

        return layout;
    }

    private Button createInventoryButton(){
        Button button = new TextButton(INVENTORY_BTN_NAME, AssetManager.instance.getDefaultSkin(), "checkable");
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CentralPanel centralPanel = (CentralPanel) (UIManager.instance.getPanel(UIManager.CENTRAL_PANEL));
                InventoryMenu menu = new InventoryMenu();//TODO: consider to add it in the UI manager rather than create new instance
                centralPanel.setActor(menu);
                UIManager.instance.setMenuOpened(true);
                checkButton(BottomToolbar.INVENTORY_BTN_NAME);
            }
        });
        buttons.put(INVENTORY_BTN_NAME, button);
        return button;
    }

    private Button createDiscoveryButton(){
        Button button = new TextButton(DISCOVERY_BTN_NAME, AssetManager.instance.getDefaultSkin(), "checkable");
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /*if(GameModel.instance.getCurrentMap().isGlobal() &&
                        (GameModel.instance.getCurrentCell().isExit())){
                    GameController.INSTANCE.changeToLocal();
                    checkButton(UNCHECK_ALL);
                } else{
                    CentralPanel centralPanel = (CentralPanel) (UIManager.instance.getPanel(UIManager.CENTRAL_PANEL));
                    InventoryMenu menu = new InventoryMenu(GameModel.instance.getCurrentCell().getItems());//TODO: consider to add it in the UI manager rather than create new instance
                    menu.setDiscoveryPanel();
                    centralPanel.setActor(menu);
                    UIManager.instance.setMenuOpened(true);
                    checkButton(BottomToolbar.DISCOVERY_BTN_NAME);
                }*/
            }
        });
        buttons.put(DISCOVERY_BTN_NAME, button);
        return button;
    }

    private Button createPersInfoButton(){
        Button button = new TextButton(PERS_BTN_NAME, AssetManager.instance.getDefaultSkin(), "checkable");
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CentralPanel centralPanel = (CentralPanel) (UIManager.instance.getPanel(UIManager.CENTRAL_PANEL));
                centralPanel.setActor(new PersMenu());
                UIManager.instance.setMenuOpened(true);
                checkButton(BottomToolbar.PERS_BTN_NAME);
            }
        });
        buttons.put(PERS_BTN_NAME, button);
        return button;
    }

    private Button createCraftButton(){
        Button button = new TextButton(CRAFT_BTN_NAME, AssetManager.instance.getDefaultSkin(), "checkable");
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CentralPanel centralPanel = (CentralPanel) (UIManager.instance.getPanel(UIManager.CENTRAL_PANEL));
                //centralPanel.setActor(new CraftMenu());
                UIManager.instance.setMenuOpened(true);
                checkButton(BottomToolbar.CRAFT_BTN_NAME);
            }
        });
        buttons.put(CRAFT_BTN_NAME, button);
        return button;
    }

    private Button createJournalButton(){
        Button button = new TextButton(JOURNAL_BTN_NAME, AssetManager.instance.getDefaultSkin(), "checkable");
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CentralPanel centralPanel = (CentralPanel) (UIManager.instance.getPanel(UIManager.CENTRAL_PANEL));
                centralPanel.setActor(new JournalMenu());
                UIManager.instance.setMenuOpened(true);
                checkButton(BottomToolbar.JOURNAL_BTN_NAME);
            }
        });
        buttons.put(JOURNAL_BTN_NAME, button);
        return button;
    }

    private Actor createConsole(){
        Console console = new Console(GameModel.instance.date);

        UIManager.instance.putPanel(UIManager.CONSOLE, console);
        return console;
    }

    //check button on menu open. Pass UNCHECK_ALL to uncheck all
    public void checkButton(String btnName){
        for (Map.Entry<String, Button> button : buttons.entrySet()) {
            if(button.getKey().equals(btnName)){
                button.getValue().setChecked(true);
            } else {
                button.getValue().setChecked(false);
            }
        }
    }
}
