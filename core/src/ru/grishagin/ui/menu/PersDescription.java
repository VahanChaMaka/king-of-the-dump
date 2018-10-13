package ru.grishagin.ui.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.grishagin.GameModel;
import ru.grishagin.ui.Utils.AssetManager;

/**
 * Created by Admin on 12.04.2018.
 */
public class PersDescription extends Container {
    private Label infoLabel;

    public PersDescription() {
        infoLabel = new Label(GameModel.getInstance().getPers().toString(),
                AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        infoLabel.setWrap(true);
        setActor(infoLabel);
        fill();
    }
}
