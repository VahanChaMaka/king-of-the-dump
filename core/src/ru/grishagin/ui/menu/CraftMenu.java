package ru.grishagin.ui.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.grishagin.Craft.CraftWizardInterface;
import ru.grishagin.Craft.Recipes;
import ru.grishagin.GameModel;
import ru.grishagin.Items.Item;
import ru.grishagin.ui.Utils.AssetManager;
import ru.grishagin.ui.Utils.LayoutUtils;

import java.util.Set;

/**
 * Created by Admin on 14.01.2018.
 */
public class CraftMenu extends BasicMenu {

    private Table itemsGrid = new Table();
    private CraftWizardInterface craftWizard = GameModel.getInstance().getCraftWizard();
    private static final int ITEM_GRID_SIZE = 4;

    public CraftMenu(){
        super();
        createMainLayout();
    }

    @Override
    protected Table createLeftPanel(){
        Table layout = super.createLeftPanel();

        itemsGrid = createItemsGrid();
        layout.add(itemsGrid).expand().pad(5);

        return layout;
    }

    private Table createItemsGrid(){
        itemsGrid.pad(2);
        itemsGrid.defaults().pad(2);

        Set<String> craftList = Recipes.getAllRecipeNames();

        int i = 0;
        for (String s : craftList) {
            Actor icon = ru.grishagin.ui.actors.menus.ItemIcon.getItemIcon(Item.makeItemByName(s, 1));

            if(i%ITEM_GRID_SIZE == 0 && i != 0){
                itemsGrid.row();
            }

            itemsGrid.add(icon).size(64, 64);
            icon.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setCraftPanel(s);
                }
            });
            i++;
        }

        return itemsGrid;
    }

    private void setCraftPanel(String name){
        Table layout = new Table();

        Item i = Item.makeItemByName(name, 1);

        Label descriptionLabel = new Label(i.getDescriptionForCraftPanel(), AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        descriptionLabel.setWrap(true);
        layout.add(descriptionLabel).width(200);
        layout.row();

        Label componentsLabel = new Label(craftWizard.compareRecipeAndAvailableComponents(name),
                AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        componentsLabel.setWrap(true);
        layout.add(componentsLabel).width(200);
        layout.row();

        Button button = new TextButton("Создать", AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameModel.getInstance().getPers().pickUp(craftWizard.craftItemByName(name));
                componentsLabel.setText(craftWizard.compareRecipeAndAvailableComponents(name));
                button.setTouchable(craftWizard.checkRequirementsForName(name)? Touchable.enabled: Touchable.disabled);
            }
        });
        button.setTouchable(craftWizard.checkRequirementsForName(name)? Touchable.enabled: Touchable.disabled);
        LayoutUtils.applyButtonSize(layout.add(button));

        layout.pad(3);
        layout.debugAll();
        rightContainer.setActor(layout);
        rightContainer.fill();
    }
}
