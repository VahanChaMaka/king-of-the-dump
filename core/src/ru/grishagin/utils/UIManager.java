package ru.grishagin.utils;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
import java.util.Map;

public class UIManager {
    private static UIManager instance;
    private Map<String, Actor> UIcomponents = new HashMap<>();
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

    public Actor getPanel(String name){
        return UIcomponents.get(name);
    }

    public void putPanel(String name, Actor panel){
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

    public static UIManager getInstance(){
        if (instance != null){
            return instance;
        } else{
            instance = new UIManager();
            return instance;
        }
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