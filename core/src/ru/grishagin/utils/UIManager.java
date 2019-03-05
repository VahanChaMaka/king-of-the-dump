package ru.grishagin.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.model.GameModel;
import ru.grishagin.ui.CentralPanel;
import ru.grishagin.ui.menu.InventoryMenu;
import ru.grishagin.ui.menu.TransferMenu;
import ru.grishagin.ui.toolbar.Console;

import java.util.HashMap;
import java.util.Map;

public class UIManager {
    public static final UIManager instance = new UIManager();
    private Map<String, WidgetGroup> UIcomponents = new HashMap<>();
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private Map<String, Stage> stages = new HashMap<>();
    //ActionProgressBar progressBar;

    private boolean isMenuOpened = false;
    private boolean isInputDisabled = false;

    //public static final String ITEMS_GRID = "itemsGrid"; probably no need in access to the imems grid
    public static final String ITEM_INFO = "itemInfo";
    public static final String BOTTOM_TOOLBAR = "bottomToolbar";
    public static final String CONSOLE = "console";
    public static final String CENTRAL_PANEL = "centralPanel";
    public static final String MAIN_STAGE = "mainStage";
    public static final String CURRENT_INVENTORY_MENU = "inventoryMenu";
    public static final String STAT_PANEL = "statPanel";

    private UIManager(){
    }

    public WidgetGroup getPanel(String name){
        return UIcomponents.get(name);
    }

    public void putPanel(String name, WidgetGroup panel){
        UIcomponents.put(name, panel);
    }

    public Stage getStage(String name){
        return stages.get(name);
    }

    public void putStage(String name, Stage panel){
        stages.put(name, panel);
    }

    public void removePanel(String name){
        UIcomponents.remove(name);
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public boolean isMenuOpened() {
        return isMenuOpened;
    }

    public void setMenuOpened(boolean menuOpened) {
        getPanel(CENTRAL_PANEL).setVisible(menuOpened);
        isMenuOpened = menuOpened;
    }

    public boolean isInputDisabled() {
        return isInputDisabled;
    }

    public void setInputDisabled(boolean inputDisabled) {
        isInputDisabled = inputDisabled;
    }

    public void openTransferWindow(Entity target, Entity player){
        Container centralPanel = (Container) getPanel(CENTRAL_PANEL);

        //TODO: consider to add it in the UI manager rather than create new instance
        TransferMenu menu = new TransferMenu(target, player);
        centralPanel.setActor(menu);
        setMenuOpened(true);
    }

    public void printMessageInConsole(String message){
        ((Console)UIcomponents.get(CONSOLE)).printMessage(message);
    }

    /*public ActionProgressBar getProgressBar(){
        if(progressBar == null) {
            progressBar = new ActionProgressBar();
            progressBar.setVisible(false); //invisible by default because bar could be showed later
        }
        return progressBar;
    }

    //updates all global time changing UI components
    public void update(float delta){
        if(progressBar != null && progressBar.isVisible()) {
            progressBar.setValue(progressBar.getValue() + delta);
        }

        //TODO: think about interface with update() method
        ((StatPanel)getPanel(STAT_PANEL)).updateStat();
        ((Console)getPanel(CONSOLE)).update();
    }*/
}