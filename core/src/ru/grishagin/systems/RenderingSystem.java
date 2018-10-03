package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.TextureComponent;
import ru.grishagin.view.RenderComparator;

public class RenderingSystem extends SortedIteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);

    private Batch batch;

    public RenderingSystem(Batch batch){
        super(Family.all(PositionComponent.class, TextureComponent.class).get(), new RenderComparator());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector3 renderPosition = new Vector3();
        PositionComponent position = pm.get(entity);
        /*if(GameModel.getInstance().getCurrentMap().isGlobal()) {//non-isometric view
            renderPosition.x = posX;
            renderPosition.y = posY;
        } else {//Isometric view*/
            int posY = -(int) (position.x * 32);
            int posX = (int) (position.y * 32);
            renderPosition.x = (posX - posY);
            renderPosition.y = (posX + posY) / 2;
        //}

        batch.begin();
        batch.draw(tm.get(entity).region, renderPosition.x - 16, renderPosition.y, 32, 32);
        batch.end();
    }
}

