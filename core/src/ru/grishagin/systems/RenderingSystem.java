package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.SpriteComponent;
import ru.grishagin.view.RenderComparator;

public class RenderingSystem extends SortedIteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SpriteComponent> tm = ComponentMapper.getFor(SpriteComponent.class);

    private boolean isoMode = true;
    private Batch batch;

    public RenderingSystem(Batch batch){
        super(Family.all(PositionComponent.class, SpriteComponent.class).get(), new RenderComparator());
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        forceSort();
        super.update(deltaTime);
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

        SpriteComponent spriteComponent = tm.get(entity);
        Vector2 offset = tm.get(entity).offset;

        batch.begin();
        batch.draw(spriteComponent.sprite, renderPosition.x + offset.x, renderPosition.y + offset.y,
                spriteComponent.sprite.getWidth(), spriteComponent.sprite.getHeight());
        batch.end();
    }
}

