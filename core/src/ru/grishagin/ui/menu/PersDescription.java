package ru.grishagin.ui.menu;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.grishagin.components.NameComponent;
import ru.grishagin.model.GameModel;
import ru.grishagin.utils.AssetManager;

/**
 * Created by Admin on 12.04.2018.
 */
public class PersDescription extends Container {
    private Label infoLabel;

    public PersDescription() {
        Entity player = GameModel.instance.getPlayer();
        infoLabel = new Label(player.getComponent(NameComponent.class).name,
                AssetManager.instance.getDefaultSkin());
        infoLabel.setWrap(true);
        setActor(infoLabel);
        fill();
    }
}
