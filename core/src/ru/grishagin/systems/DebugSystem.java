package ru.grishagin.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.FloatingTextComponent;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.SpriteComponent;
import ru.grishagin.utils.AssetManager;

import java.util.LinkedList;
import java.util.List;

public class DebugSystem extends EntitySystem {

    private final List<Entity> shapes = new LinkedList<>();
    private final List<Entity> grid = new LinkedList<>();

    public void addRect(Vector2 position){
        Entity rect = new Entity();
        rect.add(new PositionComponent(position));
        rect.add(new SpriteComponent(new Sprite(AssetManager.instance.getTexture("misc/rect.png"))));
        getEngine().addEntity(rect);
        shapes.add(rect);
    }

    public void drawGrid(){
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                PositionComponent position = new PositionComponent(i, j);

                SpriteComponent sprite = new SpriteComponent(new Sprite(AssetManager.instance.getTexture("misc/gridOutline.png")));
                sprite.renderingOrder = 1;
                Entity gridCell = new Entity();
                gridCell.add(position);
                gridCell.add(sprite);
                getEngine().addEntity(gridCell);
                grid.add(gridCell);

                Entity textCoord = new Entity();
                textCoord.add(new FloatingTextComponent(position.toString()));
                textCoord.add(position);
                getEngine().addEntity(textCoord);
                grid.add(textCoord);
            }
        }
    }

    public void clear(){
        for (Entity shape : shapes) {
            getEngine().removeEntity(shape);
        }
        shapes.clear();
    }
}
