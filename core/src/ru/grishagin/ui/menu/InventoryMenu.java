package ru.grishagin.ui.menu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.Items.Item;
import ru.grishagin.Items.ItemType;
import ru.grishagin.Pers.Inventory;
import ru.grishagin.ui.Utils.AssetManager;
import ru.grishagin.ui.Utils.LayoutUtils;

import java.util.List;

/**
 * Created by Admin on 14.09.2017.
 */
public class InventoryMenu extends BasicMenu {
    public static final String PAGER_BUTTON_IMAGE = "icon_back";
    public static final String PAGER_BUTTON_PRESSED_IMAGE = "icon_back_over";

    private Inventory items;
    private ItemType filter = ItemType.ALL;
    private int page = 1;

    private Table itemsGrid = new Table();

    private static final int ITEM_GRID_SIZE = 4;

    public InventoryMenu(Inventory items){
        super();
        this.items = items;
        createMainLayout();
    }

    public void setDiscoveryPanel(){
        rightContainer.setActor(new ru.grishagin.ui.actors.menus.DiscoveryPanel());
        rightContainer.fill();
    }

    public void setItemInfoPanel(Item item){
        rightContainer.setActor(new ru.grishagin.ui.actors.menus.ItemDescription(item, items.getPlacement()));
        rightContainer.fill();
    }

    public void setPersInfoPanel(){
        rightContainer.setActor(new PersDescription());
        rightContainer.fill();
    }

    @Override
    protected Table createLeftPanel(){
        Table layout = super.createLeftPanel();

        updateItemGrid();
        layout.add(itemsGrid).expand().fill().pad(5);

        return layout;
    }

    private Table createItemsGrid(){
        itemsGrid.pad(2);
        itemsGrid.defaults().pad(2);

        List<Item> itemsToShow = items.getItemList(filter);

        int i = (page - 1)*ITEM_GRID_SIZE*ITEM_GRID_SIZE;
        int sizeOnPage;
        if(itemsToShow.size() < ITEM_GRID_SIZE*ITEM_GRID_SIZE*page){
            sizeOnPage = itemsToShow.size();
        } else {
            sizeOnPage = ITEM_GRID_SIZE*ITEM_GRID_SIZE;
        }
        for (; i < sizeOnPage; i++) {
            Item item = itemsToShow.get(i);

            Actor icon = ru.grishagin.ui.actors.menus.ItemIcon.getItemIcon(item);

            if(i%ITEM_GRID_SIZE == 0 && i != 0){
                itemsGrid.row();
            }

            itemsGrid.add(icon).size(64, 64);
            icon.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //rightContainer.setActor(createItemInfoPanel(item));
                    setItemInfoPanel(item);
                    System.out.println(item.getName());
                }
            });
        }

        //fill empty cells
        for (; i < ITEM_GRID_SIZE*ITEM_GRID_SIZE*page; i++){
            Actor icon = ru.grishagin.ui.actors.menus.ItemIcon.getItemIcon(null);

            if(i%ITEM_GRID_SIZE == 0 && i != 0){
                itemsGrid.row();
            }

            itemsGrid.add(icon).size(64, 64);
        }


        itemsGrid.row();
        itemsGrid.add(createPager(itemsToShow.size())).colspan(ITEM_GRID_SIZE).expandX().fill();//TODO: consider to create pager only once

        return itemsGrid;
    }

    @Override
    protected Table createItemsFilterToolbar(){
        Table toolbar = super.createItemsFilterToolbar();
        toolbar.debugAll();
        toolbar.defaults().pad(3);

        for (ItemType itemType : ItemType.values()) {
            LayoutUtils.applyButtonSize(toolbar.add(createFilterButton(itemType)));
            toolbar.row();
        }

        return toolbar;
    }

    private Button createFilterButton(ItemType itemType){
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
    }

    public void updateItemGrid(){
        for (Actor actor : itemsFilterToolbar.getChildren()) {
            ((Button) actor).setChecked(((TextButton)actor).getText().toString().equals(filter.toString()));
        }
        itemsGrid.clear();
        itemsGrid = createItemsGrid();
    }

    private Table createPager(int itemsFullSize){
        Table layout = new Table();

        Sprite previousPageSprite = new Sprite(AssetManager.getInstance().getTextureRegion(PAGER_BUTTON_IMAGE));
        previousPageSprite.flip(true, false);
        Sprite previousPageSpritePressed = new Sprite(AssetManager.getInstance().getTextureRegion(PAGER_BUTTON_PRESSED_IMAGE));
        previousPageSpritePressed.flip(true, false);
        ImageButton previousPage = new ImageButton(new TextureRegionDrawable(previousPageSprite),
                new TextureRegionDrawable(previousPageSpritePressed));
        previousPage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (page != 1){
                    page--;
                    updateItemGrid();
                }
            }
        });

        int fullPageAmount = itemsFullSize / (ITEM_GRID_SIZE*ITEM_GRID_SIZE) + 1;
        if(itemsFullSize == (ITEM_GRID_SIZE*ITEM_GRID_SIZE)){
            fullPageAmount--;
        }
        final int finalFullPageAmount = fullPageAmount;
        Label currentPage = new Label(page + "/" + fullPageAmount, AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));

        ImageButton nextPage = new ImageButton(new TextureRegionDrawable(AssetManager.getInstance().getTextureRegion(PAGER_BUTTON_IMAGE)),
                new TextureRegionDrawable(AssetManager.getInstance().getTextureRegion(PAGER_BUTTON_PRESSED_IMAGE)));
        nextPage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(page != finalFullPageAmount){
                    page++;
                    updateItemGrid();
                }
            }
        });

        layout.add(previousPage).align(Align.left).size(30, 30).expand();
        layout.add(currentPage).align(Align.center).expand();
        layout.add(nextPage).align(Align.right).size(30, 30).expand();

        return layout;
    }
}