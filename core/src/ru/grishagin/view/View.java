package ru.grishagin.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.grishagin.model.InputHandler;
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

        controller = new MapInputController(camera, map, new InputHandler(GameModel.instance.engine, map));
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

    public void moveCameraTo(Vector2 modelCoords){
        Vector2 cameraPosition = new Vector2();
        float x = modelCoords.x;
        float y = modelCoords.y;
        float posY = - x * map.getTileHeight();
        float posX =  y * map.getTileHeight();
        cameraPosition.x = (posX - posY);
        cameraPosition.y = (posX + posY) / 2;

        //Vector3 screenCoords = camera.project(new Vector3(cameraPosition, 0));
        Vector3 screenCoords = new Vector3(cameraPosition, 0);
        camera.position.x = screenCoords.x;
        camera.position.y = screenCoords.y;
    }
}
