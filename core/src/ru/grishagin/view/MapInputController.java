package ru.grishagin.view;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import ru.grishagin.model.InputHandler;
import ru.grishagin.ui.toolbar.BottomToolbar;
import ru.grishagin.utils.UIManager;

public class MapInputController extends InputAdapter {
    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    private InputHandler inputHandler;
    private TiledRenderingEngine map;

    private boolean isTouchedDown = false;

    public MapInputController(OrthographicCamera camera, TiledRenderingEngine map, InputHandler inputHandler) {
        this.camera = camera;
        this.map = map;
        this.inputHandler = inputHandler;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        if (UIManager.instance.isInputDisabled()){
            return true;
        } else {
            isTouchedDown = false;

            camera.unproject(curr.set(x, y, 0));
            if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
                camera.unproject(delta.set(last.x, last.y, 0));
                delta.sub(curr);

                //limit camera movement by x
                if(camera.position.x + delta.x >= camera.viewportWidth / 2f
                        && camera.position.x + delta.x <= map.getWidth() - camera.viewportWidth / 2f) {
                    camera.position.add(delta.x, 0, 0); //move if in the bounds
                } else if(camera.position.x + delta.x < camera.viewportWidth / 2f){
                    camera.position.x = camera.viewportWidth / 2f; //if too left, set to left border
                } else if(camera.position.x + delta.x > map.getWidth() - camera.viewportWidth / 2f){
                    camera.position.x = map.getWidth() - camera.viewportWidth / 2f; //if too right, set to the right border
                }

                //limit camera movement by y
                float cameraY = camera.position.y;
                float offset = 0;
                if(!map.isOrtho()){
                    offset = map.getHeight()/2; //hack for ISO maps. Zero is not in the bottom of a map but on the left corner
                }

                //also compensate bottom toolbar height
                if(cameraY + delta.y  + BottomToolbar.TOOLBAR_HEIGHT >= camera.viewportHeight / 2f - offset
                        && cameraY + delta.y <= map.getHeight() - camera.viewportHeight / 2f - offset){
                    camera.position.add(0, delta.y, 0);
                } else if(cameraY + delta.y + BottomToolbar.TOOLBAR_HEIGHT < camera.viewportHeight / 2f - offset){
                    camera.position.y = camera.viewportHeight / 2f - offset - BottomToolbar.TOOLBAR_HEIGHT;
                } else if(cameraY + delta.y > map.getHeight() - camera.viewportHeight / 2f - offset){
                    camera.position.y = map.getHeight() - camera.viewportHeight / 2f - offset;
                }
            }
            last.set(x, y, 0);
            return false;
        }
    }

    private boolean isInMapBounds(float deltaX, float deltaY) {

        return camera.position.x + deltaX >= camera.viewportWidth / 2f
                && camera.position.x - deltaX <= map.getWidth() - camera.viewportWidth / 2f
                && camera.position.y + deltaY>= camera.viewportHeight / 2f
                && camera.position.y - deltaY<= map.getHeight() - camera.viewportHeight / 2f;
    }

    public void putInMapBounds() {

        if (camera.position.x < camera.viewportWidth / 2f)
            camera.position.x = camera.viewportWidth / 2f;
        else if (camera.position.x > map.getWidth() - camera.viewportWidth / 2f)
            camera.position.x = map.getWidth() - camera.viewportWidth / 2f;

        if (camera.position.y < camera.viewportHeight / 2f)
            camera.position.y = camera.viewportHeight / 2f;
        else if (camera.position.y > map.getHeight() - camera.viewportHeight / 2f)
            camera.position.y = map.getHeight() - camera.viewportHeight / 2f;

        /*stage.moveTo(
                camera.position.x,
                camera.position.y);*/

    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        if (UIManager.instance.isInputDisabled()){
            return true;
        } else {
            last.set(-1, -1, -1);
            if (isTouchedDown) {
                click(x, y);
            }
            return false;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (UIManager.instance.isInputDisabled()){
            return true;
        } else {
            isTouchedDown = true;
            return false;
        }
    }

    private void click(int x, int y){
        camera.unproject(curr.set(x, y, 0));//WTF, WHY -16????
        x =(int) curr.x;
        y = (int) curr.y;

        inputHandler.onClick(x, y);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        int x = (int) worldCoords.x;
        int y = (int) worldCoords.y;

        inputHandler.onHover(x, y);

        return true;
    }
}
