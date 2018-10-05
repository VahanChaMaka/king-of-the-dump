package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.TextureComponent;
import ru.grishagin.view.RenderComparator;

public class RenderingSystem extends SortedIteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);

    private boolean isoMode = true;
    private Batch batch;

    public RenderingSystem(Batch batch){
        super(Family.all(PositionComponent.class, TextureComponent.class).get(), new RenderComparator());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector3 renderPosition = new Vector3();
        PositionComponent position = pm.get(entity);
        if(isoMode) {//Isometric view
            int posY = -(int) (position.x * 32);
            int posX = (int) (position.y * 32);
            renderPosition.x = (posX - posY);
            renderPosition.y = (posX + posY) / 2;
        } else {//non-isometric view
            renderPosition.x = position.x;
            renderPosition.y = position.y;
        }

        TextureRegion textureRegion = tm.get(entity).region;
        Vector2 offset = tm.get(entity).offset;

        batch.begin();
        batch.draw(textureRegion, renderPosition.x + offset.x, renderPosition.y + offset.y,
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        batch.end();
    }
}

