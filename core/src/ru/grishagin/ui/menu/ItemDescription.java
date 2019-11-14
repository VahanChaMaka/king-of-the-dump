package ru.grishagin.ui.menu;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.grishagin.components.*;
import ru.grishagin.components.items.*;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.entities.ItemFactory;
import ru.grishagin.model.GameModel;
import ru.grishagin.model.map.MapFactory;
import ru.grishagin.systems.InventorySystem;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.LayoutUtils;
import ru.grishagin.utils.Logger;
import ru.grishagin.utils.UIManager;

import java.util.Map;

/**
 * Created by Admin on 10.09.2017.
 */
public class ItemDescription extends Container {
    private ComponentMapper<NameComponent> nm = ComponentMapper.getFor(NameComponent.class);
    private ComponentMapper<DescriptionComponent> dm = ComponentMapper.getFor(DescriptionComponent.class);
    private ComponentMapper<AmountComponent> am = ComponentMapper.getFor(AmountComponent.class);

    private Entity item;
    private Entity owner;
    private Entity transferTarget;

    private Label itemDescription;
    private Table layout;
    private ItemInfoSupport parent;

    public ItemDescription(Entity item, Entity owner, Entity transferTarget, ItemInfoSupport parent){
        this.item = item;
        this.owner = owner;
        this.transferTarget = transferTarget;
        this.parent = parent;

        layout = new Table();
        layout.debugAll();

        itemDescription = new Label(item.toString(), AssetManager.instance.getDefaultSkin());
        itemDescription.setWrap(true);

        layout.add(itemDescription).expand().fill();
        layout.row();

        addItemActionsButtons();

        setActor(layout);
        fill();
        UIManager.instance.putPanel(UIManager.ITEM_INFO, this);
    }

    /*
     *This method adds buttons
     * which allow to make actions with current item.
     *
     * All items have the same actions such as throw away or pick up(depends on the place of the item: in pers's inventory or on the ground)
     * and specific actions for an item type
     * and specific actions for the current item
     *
     * For example:
     * A bread could be thrown away (general Item) and could be eaten (as Food)
     */
    private void addItemActionsButtons(){
        final boolean isOwnerPlayer = owner.getComponent(PlayerControlled.class) != null;

        TextButton throwOrPickButton = new TextButton("", AssetManager.instance.getDefaultSkin());
        if( isOwnerPlayer && transferTarget != null){
            throwOrPickButton.setText("Положить");
        } else if(transferTarget != null && transferTarget.getComponent(PlayerControlled.class) != null ) {
            throwOrPickButton.setText("Забрать");
        } else {
            throwOrPickButton.setText("Выбросить");
            //TODO: set transferTarget to current cell!
        }
        throwOrPickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameModel.instance.inventorySystem.transferItem(owner, transferTarget,
                        item.getComponent(TypeIdComponent.class).id);
                parent.update();
            }
        });

        layout.row();
        LayoutUtils.applyButtonSize(layout.add(throwOrPickButton));


        //probably no need to special button for this, may be choose amount like in Skyrim or split like in Underrail
        /*if(item.getAmount() > 1) {
            TextButton throwOrPickAllButton = new TextButton("", AssetManager.instance.getDefaultSkin());
            if(placement == Inventory.ON_GROUND){
                throwOrPickAllButton.setText("Поднять все");
                throwOrPickAllButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //GameController.INSTANCE.pickAllUp(item);
                    }
                });
            } else {
                throwOrPickAllButton.setText("Выбросить все");
                throwOrPickAllButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameController.INSTANCE.throwAllAway(item);
                    }
                });
            }
            layout.row();
            LayoutUtils.applyButtonSize(layout.add(throwOrPickAllButton));
        }*/

        if(item.getComponent(ConsumableComponent.class) != null){
            TextButton consumeButton = new TextButton("", AssetManager.instance.getDefaultSkin());

            if(item.getComponent(DrinkTag.class) != null){
                consumeButton.setText("Выпить");
            } else if(item.getComponent(FoodTag.class) != null){
                consumeButton.setText("Съесть");
            } else {
                consumeButton.setText("Использовать");
            }

            consumeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //GameController.INSTANCE.consume(item, placement);
                }
            });

            layout.row();
            LayoutUtils.applyButtonSize(layout.add(consumeButton));
        }

        if(item.getComponent(DestroyableComponent.class) != null && item.getComponent(DestroyableComponent.class).isDestroyableManually){
            TextButton destroyButton = new TextButton("Разобрать", AssetManager.instance.getDefaultSkin());
            destroyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                   //GameController.INSTANCE.destroyItem(item, placement);
                }
            });

            StringBuilder components = new StringBuilder("\nКомпоненты:");
            for (Map<String, Object> component:
                    ItemFactory.getDestroyResult(item.getComponent(TypeIdComponent.class).id)) {
                components.append("\n" + "- " + ItemFactory.getItemName((Integer)component.get(ItemFactory.ID))); //key is item id
                components.append(": " + component.get(ItemFactory.AMOUNT));
            }

            Label componentsAfterDestroyLabel = new Label(components.toString(), AssetManager.instance.getDefaultSkin());

            layout.row();
            layout.add(componentsAfterDestroyLabel);
            layout.row();
            LayoutUtils.applyButtonSize(layout.add(destroyButton));
        }

        //TODO: equip not only from inventory, but from everywhere
        //TODO: unequip
        if (isOwnerPlayer && (item.getComponent(WeaponComponent.class) != null || item.getComponent(ArmorComponent.class) != null)){
            TextButton equipButton = new TextButton("Надеть", AssetManager.instance.getDefaultSkin());
            equipButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedArmorComponent equippedArmorComponent = owner.getComponent(EquippedArmorComponent.class);
                    ArmorComponent armorToEquip = item.getComponent(ArmorComponent.class);
                    if(armorToEquip != null){
                        if(armorToEquip.type == ArmorComponent.ArmorType.HEAD) {
                            equippedArmorComponent.changeHead(item);
                        } else if(armorToEquip.type == ArmorComponent.ArmorType.SUIT){
                            equippedArmorComponent.changeSuit(item);
                        } else {
                            Logger.warning(item, "Trying to equip unknown type of armor!");
                        }
                        parent.update();
                        return;
                    }

                    EquippedWeaponComponent equippedWeaponComponent = owner.getComponent(EquippedWeaponComponent.class);
                    WeaponComponent weaponToEquip = item.getComponent(WeaponComponent.class);
                    if(weaponToEquip != null){
                        equippedWeaponComponent.weapon = item;
                        parent.update();
                        return;
                    }

                    Logger.warning(item, "Trying to equip hell knows what");
                }
            });
            layout.row();
            LayoutUtils.applyButtonSize(layout.add(equipButton));
        }

        /*if(item.getName().equals("Грязная вода")){
            if(GameModel.instance.getCurrentCell().getItems().containsWithName("Костер")){
                GameModel.instance.getPers().pickUp(GameModel.instance.getCurrentCell().pickUp("Костер", 1));//take a FirePlace coz CraftWizard works with items in pers's inventory only
            }

            Label boilLabelComponents = new Label("\n" + GameModel.instance.getCraftWizard().compareRecipeAndAvailableComponents("Чистая вода"),
                    AssetManager.instance.getDefaultSkin());

            TextButton boilButton = new TextButton("Кипятить", AssetManager.instance.getDefaultSkin());
            boilButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameController.INSTANCE.boilWater(item);
                }
            });


            boilButton.setDisabled(GameModel.instance.getCraftWizard().checkRequirementsForName("Чистая вода"));

            if(GameModel.instance.getPers().getInventory().containsWithName("Костер")) {
                GameModel.instance.getCurrentCell().catchThrown(GameModel.instance.getPers().throwAway("Костер", 1));//return the FirePlace back on the ground
            }

            layout.row();
            layout.add(boilLabelComponents);
            layout.row();
            LayoutUtils.applyButtonSize(layout.add(boilButton));
        }*/
    }

    public void updateItemDescription(){
        itemDescription.setText(item.toString());
    }

}
