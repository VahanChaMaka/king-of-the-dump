package ru.grishagin.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import ru.grishagin.components.DescriptionComponent;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.components.items.DestroyableComponent;
import ru.grishagin.entities.ItemFactory;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.UIManager;

import java.util.Map;

public class ItemInfoPopup extends Window {

    private static final int POPUP_WIDTH = 250;
    private static final int POPUP_HEIGHT = 250;

    private Entity item;
    private boolean isOpened;

    public ItemInfoPopup(){
        super("", AssetManager.instance.getDefaultSkin());

        setModal(false);

        //allow input event pass through popup to lower actors (to allow exit on item icon)
        setTouchable(Touchable.disabled);

        pad(10);
        debugAll();
    }



    public void setItem(Entity item){
        this.item = item;

        clearChildren();

        getTitleLabel().setText(item.getComponent(NameComponent.class).name);
        //text(item.getComponent(DescriptionComponent.class).description);
        row();
        Label name = new Label(item.getComponent(NameComponent.class).name, AssetManager.instance.getDefaultSkin());
        add(name).fill();

        row();
        Label description = new Label(item.getComponent(DescriptionComponent.class).description, AssetManager.instance.getDefaultSkin());
        description.setWrap(true);
        add(description).expand().fill();

        //add list of components after item disassembly
        if(item.getComponent(DestroyableComponent.class) != null && item.getComponent(DestroyableComponent.class).isDestroyableManually){
            StringBuilder components = new StringBuilder("\nКомпоненты:");
            for (Map<String, Object> component:
                    ItemFactory.getDestroyResult(item.getComponent(TypeIdComponent.class).id)) {
                components.append("\n" + "- " + ItemFactory.getItemName((Integer)component.get(ItemFactory.ID))); //key is item id
                components.append(": " + component.get(ItemFactory.AMOUNT));
            }

            Label componentsAfterDestroyLabel = new Label(components.toString(), AssetManager.instance.getDefaultSkin());

            row();
            add(componentsAfterDestroyLabel);
            row();
        }

        pack();
    }

    public ItemInfoPopup show() {
        isOpened = true;
        Stage stage = UIManager.instance.getStage(UIManager.MAIN_STAGE);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));

        stage.addActor(this);
        return this;
    }

    public void hide() {
        isOpened = false;
        remove();
    }

    public boolean isOpened() {
        return isOpened;
    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return POPUP_WIDTH;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return POPUP_HEIGHT;
    }
}
