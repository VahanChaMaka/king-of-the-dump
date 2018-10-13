package ru.grishagin.ui.menu;

/**
 * Created by Admin on 14.01.2018.
 */
public class PersMenu extends BasicMenu {

    public PersMenu(){
        super();
        createMainLayout();

        rightContainer.setActor(new PersDescription());
        rightContainer.fill();
    }
}
