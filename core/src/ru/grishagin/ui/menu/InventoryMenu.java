package ru.grishagin.ui.menu;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import ru.grishagin.model.GameModel;

import java.util.List;

/**
 * Created by Admin on 14.09.2017.
 */
public class InventoryMenu extends BasicMenu {

    private List<Entity> items;
    //private ItemType filter = ItemType.ALL;
    private int page = 1;

    private ItemsGrid itemsGrid;

    public InventoryMenu(List<Entity> items){
        super();
        this.items = items;
        createMainLayout();
    }

    public void setDiscoveryPanel(){
        rightContainer.setActor(new DiscoveryPanel());
        rightContainer.fill();
    }

    public void setItemInfoPanel(Entity item){
        //rightContainer.setActor(new ItemDescription(item, items.getPlacement()));
        rightContainer.fill();
    }

    public void setPersInfoPanel(){
        rightContainer.setActor(new PersDescription());
        rightContainer.fill();
    }

    @Override
    protected Table createLeftPanel(){
        Table layout = super.createLeftPanel();

        itemsGrid = new ItemsGrid(GameModel.instance.getPlayer(), null, null);
        layout.add(itemsGrid).expand().fill().pad(5);

        return layout;
    }

    @Override
    protected Table createItemsFilterToolbar(){
        Table toolbar = super.createItemsFilterToolbar();
        toolbar.debugAll();
        toolbar.defaults().pad(3);

        /*for (ItemType itemType : ItemType.values()) {
            LayoutUtils.applyButtonSize(toolbar.add(createFilterButton(itemType)));
            toolbar.row();
        }*/

        return toolbar;
    }

    /*private Button createFilterButton(ItemType itemType){
        TextButton button = new TextButton(itemType.toString(), AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN), "checkable");
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                filter = itemType;
                page = 1;
                updateItemGrid();
            }
        });
        return button;
    }*/

    public void updateItemGrid(){
        //set filter button checked
        /*for (Actor actor : itemsFilterToolbar.getChildren()) {
            ((Button) actor).setChecked(((TextButton)actor).getText().toString().equals(filter.toString()));
        }*/
        itemsGrid.clear();
        itemsGrid.update();
    }
}