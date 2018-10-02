package ru.grishagin.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.grishagin.model.map.Map;
import ru.grishagin.utils.UIManager;

public class View{

    private ViewMap map;
    private OrthographicCamera camera;
    private BitmapFont font;
    private SpriteBatch batch;
    private MapInputController controller;

    public View(){

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        font = new BitmapFont();
        batch = new SpriteBatch();

        //map = new ViewMap(GameModel.getInstance().getCurrentMap(), batch);
        map = new ViewMap(new Map(), batch);
        //persView = new PersView(batch);
        controller = new MapInputController(camera, map);
        controller.putInMapBounds();

        UIManager.getInstance().getInputMultiplexer().addProcessor(controller);
        Gdx.input.setInputProcessor(UIManager.getInstance().getInputMultiplexer());
    }

    public ViewMap getMap() {
        return map;
    }

    public void draw() {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update camera on resize
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.update();

        map.draw(camera);
        //persView.draw(5);

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }
}