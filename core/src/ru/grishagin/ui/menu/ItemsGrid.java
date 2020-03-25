package ru.grishagin.ui.menu;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.components.NameComponent;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.UIManager;

import java.util.List;

public class ItemsGrid extends Table {

    public static final String PAGER_BUTTON_IMAGE = "icon_back";
    public static final String PAGER_BUTTON_PRESSED_IMAGE = "icon_back_over";

    private static final int ITEM_GRID_SIZE = 4;

    private Entity owner;
    private Entity transferTarget;
    private int page = 1;

    public ItemsGrid(Entity owner, Entity transferTarget) {
        this.owner = owner;
        this.transferTarget = transferTarget;
        update();
    }

    public void update(){
        clear();

        pad(2);
        defaults().pad(2);

        //List<Entity> itemsToShow = items.getItemList(filter);
        List<Entity> itemsToShow = owner.getComponent(InventoryComponent.class).items;

        int i = (page - 1)*ITEM_GRID_SIZE*ITEM_GRID_SIZE;
        int sizeOnPage;
        if(itemsToShow.size() < ITEM_GRID_SIZE*ITEM_GRID_SIZE*page){
            sizeOnPage = itemsToShow.size();
        } else {
            sizeOnPage = ITEM_GRID_SIZE*ITEM_GRID_SIZE;
        }
        for (; i < sizeOnPage; i++) {
            Entity item = itemsToShow.get(i);

            Actor icon = ItemIcon.getItemIcon(item, PanelType.ITEMS_GRID, transferTarget);

            if(i%ITEM_GRID_SIZE == 0 && i != 0){
                row();
            }

            add(icon).size(64, 64);
        }

        //fill empty cells
        for (; i < ITEM_GRID_SIZE*ITEM_GRID_SIZE*page; i++){
            Actor icon = ItemIcon.getItemIcon(null, PanelType.ITEMS_GRID, transferTarget);

            if(i%ITEM_GRID_SIZE == 0 && i != 0){
                row();
            }

            add(icon).size(64, 64);
        }


        row();
        add(createPager(itemsToShow.size())).colspan(ITEM_GRID_SIZE).expandX().fill();//TODO: consider to create pager only once
    }

    private Table createPager(int itemsFullSize){
        Table layout = new Table();

        Sprite previousPageSprite = new Sprite(AssetManager.instance.getUITexture(PAGER_BUTTON_IMAGE));
        previousPageSprite.flip(true, false);
        Sprite previousPageSpritePressed = new Sprite(AssetManager.instance.getUITexture(PAGER_BUTTON_PRESSED_IMAGE));
        previousPageSpritePressed.flip(true, false);
        ImageButton previousPage = new ImageButton(new TextureRegionDrawable(previousPageSprite),
                new TextureRegionDrawable(previousPageSpritePressed));
        previousPage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (page != 1){
                    page--;
                    update();
                }
            }
        });

        int fullPageAmount = itemsFullSize / (ITEM_GRID_SIZE*ITEM_GRID_SIZE) + 1;
        if(itemsFullSize == (ITEM_GRID_SIZE*ITEM_GRID_SIZE)){
            fullPageAmount--;
        }
        final int finalFullPageAmount = fullPageAmount;
        Label currentPage = new Label(page + "/" + fullPageAmount, AssetManager.instance.getDefaultSkin());

        ImageButton nextPage = new ImageButton(new TextureRegionDrawable(AssetManager.instance.getUITexture(PAGER_BUTTON_IMAGE)),
                new TextureRegionDrawable(AssetManager.instance.getUITexture(PAGER_BUTTON_PRESSED_IMAGE)));
        nextPage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(page != finalFullPageAmount){
                    page++;
                    update();
                }
            }
        });

        layout.add(previousPage).align(Align.left).size(30, 30).expand();
        layout.add(currentPage).align(Align.center).expand();
        layout.add(nextPage).align(Align.right).size(30, 30).expand();

        return layout;
    }

}
