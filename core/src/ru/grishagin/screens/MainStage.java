package ru.grishagin.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.grishagin.model.GameModel;
import ru.grishagin.ui.CentralPanel;
import ru.grishagin.ui.toolbar.BottomToolbar;
import ru.grishagin.utils.UIManager;

public class MainStage extends Stage {

    private Container centralPanel;

    private Container bottomToolbar;

    public MainStage(Viewport viewPort){
        this.setViewport(viewPort);

        loadDefaultSkin();

        Table stageLayout = new Table();
        stageLayout.setFillParent(true);

        centralPanel = new CentralPanel();
        UIManager.getInstance().putPanel(UIManager.CENTRAL_PANEL, centralPanel);
        stageLayout.add(centralPanel).expand().fill();

        bottomToolbar = new BottomToolbar();
        stageLayout.row();
        //stageLayout.add(bottomToolbar).bottom().right().height(BottomToolbar.TOOLBAR_HEIGHT).fill();

        //stageLayout.debugAll();
        addActor(stageLayout);

        //UIManager.getInstance().putStage(UIManager.MAIN_STAGE, this);
    }

    private void loadDefaultSkin(){
        //AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN);
    }

    @Override
    public void act(float delta) {
        //GameController.INSTANCE.update(delta);
        GameModel.instance.engine.update(delta);
        super.act(delta);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return UIManager.getInstance().isInputDisabled() || super.touchDown(screenX, screenY, pointer, button);
    }
}

