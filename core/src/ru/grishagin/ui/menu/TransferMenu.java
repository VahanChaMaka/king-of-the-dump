package ru.grishagin.ui.menu;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.components.InventoryComponent;

public class TransferMenu extends MenuFrame implements ItemInfoSupport{

    private ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);

    private Entity player;
    private Entity target;

    private Table layout;
    private Container itemInfo = new Container();
    private ItemsGrid playerItems;
    private ItemsGrid targetItems;

    public TransferMenu(Entity target, Entity player) {
        super();

        this.player = player;
        this.target = target;

        setupContent();
        layout.debugAll();
    }

    @Override
    protected Actor createContent() {
        itemInfo.fill();

        playerItems = new ItemsGrid(target, player, this);
        targetItems = new ItemsGrid(player, target, this);

        layout = new Table();

        layout.add(playerItems).expand().fill();
        layout.add(itemInfo).width(200).expandY().fillY();
        layout.add(targetItems).expand().fill();

        return layout;
    }

    @Override
    public void showInfo(Entity item, Entity owner, Entity target) {
        itemInfo.setActor(new ItemDescription(item, owner, target, this));
    }

    @Override
    public void update() {
        playerItems.update();
        targetItems.update();
        itemInfo.clear();
    }
}
