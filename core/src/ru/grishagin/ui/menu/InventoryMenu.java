package ru.grishagin.ui.menu;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import ru.grishagin.components.EquippedArmorComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.model.GameModel;
import ru.grishagin.model.messages.MessageType;
import ru.grishagin.utils.AssetManager;

import java.util.List;

/**
 * Created by Admin on 14.09.2017.
 */
public class InventoryMenu extends BasicMenu {

    //private ItemType filter = ItemType.ALL;
    private int page = 1;

    private ItemsGrid itemsGrid;
    private Container itemInfo = new Container();

    public InventoryMenu(){
        super();
        createMainLayout();

        MessageManager.getInstance().addListener(msg -> {
            updateItemGrid();
            rightContainer.setActor(createRightPanel());
            return true;
        }, MessageType.UI_UPDATE);
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

        itemsGrid = new ItemsGrid(GameModel.instance.getPlayer(), null);
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

    @Override
    protected Table createRightPanel() {
        return createEquipPanel();
    }

    private Table createEquipPanel(){
        EquippedArmorComponent equip = GameModel.instance.getPlayer().getComponent(EquippedArmorComponent.class);
        Table equipLayout = new Table();
        equipLayout.debugAll();
        equipLayout.pad(10);

        equipLayout.row();

        Label headLabel = new Label("Шляпа", AssetManager.instance.getDefaultSkin());
        equipLayout.add(headLabel);
        Entity head = equip.getHead();
        //typedIdComponent == null means it's default head without icon
        if(head.getComponent(TypeIdComponent.class) == null){
            head = null;
        }
        Actor equippedHeadIcon = ItemIcon.getItemIcon(head, PanelType.EQUIP_PANEL, null);
        equipLayout.add(equippedHeadIcon);

        equipLayout.row();

        Label bodyLabel = new Label("Тело", AssetManager.instance.getDefaultSkin());
        equipLayout.add(bodyLabel);
        Entity suit = equip.getSuit();
        if(suit.getComponent(TypeIdComponent.class) == null){
            suit = null;
        }
        Actor equippedBodyIcon = ItemIcon.getItemIcon(suit, PanelType.EQUIP_PANEL, null);
        equipLayout.add(equippedBodyIcon);

        return equipLayout;
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