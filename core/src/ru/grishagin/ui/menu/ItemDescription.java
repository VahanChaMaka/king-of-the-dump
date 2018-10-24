package ru.grishagin.ui.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Admin on 10.09.2017.
 */
public class ItemDescription extends Container {
    /*private Item item;
    private int placement;

    Label itemDescription;
    Table layout;

    public ItemDescription(Item item, int placement){
        this.item = item;
        this.placement = placement;

        layout = new Table();
        layout.debugAll();

        itemDescription = new Label(item.toString(), AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        itemDescription.setWrap(true);

        layout.add(itemDescription).expand().fill();
        layout.row();

        addItemActionsButtons();

        setActor(layout);
        fill();
        UIManager.getInstance().putPanel(UIManager.ITEM_INFO, this);
    }

    *//*
     *This method adds buttons
     * which allow to make actions with current item.
     *
     * All items have the same actions such as throw away or pick up(depends on the place of the item: in pers's inventory or on the ground)
     * and specific actions for an item type
     * and specific actions for the current item
     *
     * For example:
     * A bread could be thrown away (general Item) and could be eaten (as Food)
     *//*
    private void addItemActionsButtons(){
        TextButton throwOrPickButton = new TextButton("", AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        if(placement == Inventory.ON_GROUND){
            throwOrPickButton.setText("Поднять");
            throwOrPickButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameController.INSTANCE.pickUpItem(item);
                }
            });
        }else {
            throwOrPickButton.setText("Выбросить");
            throwOrPickButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameController.INSTANCE.throwItemAway(item);
                }
            });
        }
        layout.row();
        LayoutUtils.applyButtonSize(layout.add(throwOrPickButton));

        if(item.getAmount() > 1) {
            TextButton throwOrPickAllButton = new TextButton("", AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
            if(placement == Inventory.ON_GROUND){
                throwOrPickAllButton.setText("Поднять все");
                throwOrPickAllButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameController.INSTANCE.pickAllUp(item);
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
        }

        if(item instanceof Food){
            TextButton consumeButton = new TextButton("", AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));

            if(((Food) item).getFoodType() == Food.DRINKABLE){
                consumeButton.setText("Выпить");
            } else if(((Food) item).getFoodType() == Food.EATABLE){
                consumeButton.setText("Съесть");
            }

            consumeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameController.INSTANCE.consume(item, placement);
                }
            });

            layout.row();
            LayoutUtils.applyButtonSize(layout.add(consumeButton));
        }

        if(item.isDestroyable() && item.isDestroyableManually()){
            TextButton destroyButton = new TextButton("Разобрать", AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
            destroyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                   GameController.INSTANCE.destroyItem(item, placement);
                }
            });

            StringBuilder components = new StringBuilder("\nКомпоненты:");
            for (String s:
                    item.getDestroyResult().keySet()) {
                components.append("\n" + "- " + s);
            }
            components.append("");

            Label componentsAfterDestroyLabel = new Label(components.toString(), AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));

            layout.row();
            layout.add(componentsAfterDestroyLabel);
            layout.row();
            LayoutUtils.applyButtonSize(layout.add(destroyButton));
        }

        if(item.getName().equals("Грязная вода")){
            if(GameModel.getInstance().getCurrentCell().getItems().containsWithName("Костер")){
                GameModel.getInstance().getPers().pickUp(GameModel.getInstance().getCurrentCell().pickUp("Костер", 1));//take a FirePlace coz CraftWizard works with items in pers's inventory only
            }

            Label boilLabelComponents = new Label("\n" + GameModel.getInstance().getCraftWizard().compareRecipeAndAvailableComponents("Чистая вода"),
                    AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));

            TextButton boilButton = new TextButton("Кипятить", AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
            boilButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameController.INSTANCE.boilWater(item);
                }
            });


            boilButton.setDisabled(GameModel.getInstance().getCraftWizard().checkRequirementsForName("Чистая вода"));

            if(GameModel.getInstance().getPers().getInventory().containsWithName("Костер")) {
                GameModel.getInstance().getCurrentCell().catchThrown(GameModel.getInstance().getPers().throwAway("Костер", 1));//return the FirePlace back on the ground
            }

            layout.row();
            layout.add(boilLabelComponents);
            layout.row();
            LayoutUtils.applyButtonSize(layout.add(boilButton));
        }
    }

    public void updateItemDescription(){
        itemDescription.setText(item.toString());
    }*/

}
