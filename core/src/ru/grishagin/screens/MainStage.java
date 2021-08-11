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

    private final float TIME_STEP = 1f/60;

    private Container centralPanel;

    private Container bottomToolbar;

    private float accumulator = 0;

    public MainStage(Viewport viewPort){
        super(viewPort);

        //loadDefaultSkin();

        Table stageLayout = new Table();
        stageLayout.setFillParent(true);

        centralPanel = new CentralPanel();
        UIManager.instance.putPanel(UIManager.CENTRAL_PANEL, centralPanel);
        stageLayout.add(centralPanel).expand().fill();

        bottomToolbar = new BottomToolbar();
        stageLayout.row();
        stageLayout.add(bottomToolbar).bottom().right().height(BottomToolbar.TOOLBAR_HEIGHT).fill();

        //stageLayout.debugAll();
        addActor(stageLayout);

        UIManager.instance.putStage(UIManager.MAIN_STAGE, this);
    }

    private void loadDefaultSkin(){
        //AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN);
    }

    @Override
    public void act(float delta) {
        //limit maximum delta
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            GameModel.instance.engine.update(TIME_STEP);
            UIManager.instance.update(TIME_STEP);
            super.act(TIME_STEP);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return UIManager.instance.isInputDisabled() || super.touchDown(screenX, screenY, pointer, button);
    }
}

