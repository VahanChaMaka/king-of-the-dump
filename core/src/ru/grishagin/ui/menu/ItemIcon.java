package ru.grishagin.ui.menu;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.components.items.*;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.model.GameModel;
import ru.grishagin.ui.ContextMenu;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.UIManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 15.08.2017.
 */
public class ItemIcon extends Actor {
    public static final String ITEM_BACKGROUND = "button";
    public static final String ITEM_BACKGROUND_DOWN = "button_1_over";

    private ItemIcon(){}

    public static Actor getItemIcon(Entity item, PanelType context, Entity transferTarget){
        //create container anyway. If item=null container should be returned without any image
        Container<Widget> icon = new Container<>();
        icon.background(new TextureRegionDrawable(AssetManager.instance.getUITexture(ITEM_BACKGROUND)));

        if(item != null) {
            String name = item.getComponent(NameComponent.class).name;
            String[] words = name.split(" ");
            name = "";
            for (String word : words) {
                name = name + word + "\n";
            }

            //change background on press/release
            icon.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(event.getButton() == Input.Buttons.RIGHT){
                        UIManager.instance.hideItemInfoPopup();
                        ContextMenu menu = new ContextMenu();
                        List<MenuAction> actions = buildActionsList(item, context, transferTarget);
                        menu.setActions(actions);
                        menu.setPosition(event.getStageX(), event.getStageY());
                    }
                    icon.background(new TextureRegionDrawable(AssetManager.instance.getUITexture(ITEM_BACKGROUND_DOWN)));
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    icon.background(new TextureRegionDrawable(AssetManager.instance.getUITexture(ITEM_BACKGROUND)));
                    super.touchUp(event, x, y, pointer, button);
                }
            });

            //show info popup on mouse hover
            icon.addListener(new InputListener(){
                @Override
                public boolean mouseMoved(InputEvent event, float x, float y) {
                    UIManager.instance.showItemInfoPopup(item);
                    return true;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    UIManager.instance.hideItemInfoPopup();
                }
            });

            //fill container
            TextureRegion texture = AssetManager.instance.getIcon(item.getComponent(TypeIdComponent.class).id);
            if (texture != null) {
                icon.setActor(new Image(texture));
                icon.size(50, 50);
            } else {//if item has no image, create simple text button
                Label label = new Label(name, AssetManager.instance.getDefaultSkin());
                float scale = 55 / label.getWidth();
                if (scale > 1) {
                    scale = 1;
                }
                label.setFontScale(scale);
                label.setAlignment(Align.center);
                icon.setActor(label);
                icon.fill();
            }
        }
        return icon;
    }

    private static List<MenuAction> buildActionsList(Entity item, PanelType context, Entity transferTarget){
        List<MenuAction> actions = new LinkedList<>();

        if(item.getComponent(ConsumableComponent.class) != null){
            actions.add(new MenuAction("Использовать",() -> {
                //GameController.INSTANCE.consume(item, placement);
            }));
        }

        Entity owner = item.getComponent(OwnerComponent.class).owner;
        final boolean isOwnerByPlayer = owner.getComponent(PlayerControlled.class) != null;

        //transfer
        if(isOwnerByPlayer) {
            if(transferTarget != null){
                actions.add(new MenuAction("Положить", () -> {
                    GameModel.instance.inventorySystem.transferItem(GameModel.instance.getPlayer(), transferTarget,
                            item.getComponent(TypeIdComponent.class).id);
                }));
            }

            actions.add(new MenuAction("Выбросить", () -> {
                //TODO: implement
            }));
        } else {
            actions.add(new MenuAction("Взять", () -> {
                GameModel.instance.inventorySystem.transferItem(owner, GameModel.instance.getPlayer(),
                        item.getComponent(TypeIdComponent.class).id);
            }));
        }
        //TODO: implement transfer from player to another container

        //split
        actions.add(new MenuAction("Разделить", () -> {
            //show split dialog
        }));

        //disassemble
        if (item.getComponent(DestroyableComponent.class) != null && item.getComponent(DestroyableComponent.class).isDestroyableManually) {
            actions.add(new MenuAction("Разобрать", () -> {
                //GameController.INSTANCE.destroyItem(item, placement);
            }));
        }

        //equip
        if(item.getComponent(WeaponComponent.class) != null || item.getComponent(ArmorComponent.class) != null){
            if(context == PanelType.ITEMS_GRID) {
                actions.add(new MenuAction("Надеть", () -> {
                    GameModel.instance.inventorySystem.equipItem(item);
                }));
            } else {
                actions.add(new MenuAction("Снять", () -> {
                    GameModel.instance.inventorySystem.takeOffItem(item);
                }));
            }

        }

        return actions;
    }

}
