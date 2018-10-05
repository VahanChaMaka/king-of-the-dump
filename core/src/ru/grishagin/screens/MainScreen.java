package ru.grishagin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.grishagin.utils.UIManager;

public class MainScreen extends ScreenAdapter {
    //public static final int WIDTH = 1280;
    //public static final int HEIGHT = 720;

    private final int WIDTH = 200;
    private final int HEIGHT = 100;

    private Stage stage;

    public MainScreen() {
        Gdx.graphics.setWindowedMode(WIDTH, HEIGHT);
        Viewport viewport = new ScreenViewport();
        stage = new MainStage(viewport);
        UIManager.getInstance().getInputMultiplexer().addProcessor(stage);

        //if any UI is opened consume all input events and do not let them to reach map
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return UIManager.getInstance().isMenuOpened() ||
                        y < UIManager.getInstance().getPanel(UIManager.BOTTOM_TOOLBAR).getHeight();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 0, 0);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
