package ru.grishagin.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.grishagin.model.ClickHandler;
import ru.grishagin.model.GameModel;
import ru.grishagin.utils.UIManager;

public class View{

    private TiledRenderingEngine map;
    private OrthographicCamera camera;
    private BitmapFont font;
    private SpriteBatch batch;
    private MapInputController controller;

    public View(){

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        font = new BitmapFont();
        batch = new SpriteBatch();

        map = new TiledRenderingEngine(GameModel.instance.getCurrentMap().getMap(), GameModel.instance.engine);

        controller = new MapInputController(camera, map, new ClickHandler(GameModel.instance.engine));
        controller.putInMapBounds();

        UIManager.instance.getInputMultiplexer().addProcessor(controller);
        Gdx.input.setInputProcessor(UIManager.instance.getInputMultiplexer());
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
        //font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }
}
