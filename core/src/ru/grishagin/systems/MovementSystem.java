package ru.grishagin.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.DirectionComponent;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.VelocityComponent;
import ru.grishagin.components.tags.ImpassableComponent;
import ru.grishagin.model.GameModel;

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<DirectionComponent> dm = ComponentMapper.getFor(DirectionComponent.class);

    public MovementSystem(){
        super(Family.all(DirectionComponent.class, PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        setVelocity(entity);
        PositionComponent position = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);

        if(canMakeStep(position, velocity, deltaTime)) {
            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
        } else {
            entity.remove(DirectionComponent.class);
        }
    }

    private void setVelocity(Entity entity){
        PositionComponent position = pm.get(entity);
        DirectionComponent direction = dm.get(entity);
        VelocityComponent velocity = vm.get(entity);

        float deltaX = Math.abs(direction.x - position.x);
        float deltaY = Math.abs(direction.y - position.y);
        float distance = (float)Math.sqrt(deltaX*deltaX + deltaY*deltaY);

        if(distance != 0) {
            //timer.setScaleFactor(Timer.QUICK_SCALE);//speed up time

            float sin, cos;
            sin = deltaY / distance;
            cos = deltaX / distance;

            velocity.x = velocity.speed * cos;
            velocity.y = velocity.speed * sin;

            if (position.x != direction.x && (int) ((direction.x - position.x) / velocity.x) != 0) {
                if (direction.x - position.x > 0) {
                    position.x += velocity.x;
                } else if (direction.x - position.x < 0) {
                    position.x -= velocity.x;
                }
            } else {
                position.x = direction.x;
                stop(entity);
            }

            if (direction.y != position.y && (int) ((direction.y - position.y) / velocity.y) != 0) {
                if (direction.y - position.y > 0) {
                    position.y += velocity.y;
                } else if (direction.y - position.y < 0) {
                    position.y -= velocity.y;
                }
            } else {
                position.y = direction.y;
                stop(entity);
            }

            /*if(position.x == direction.x && position.y == direction.y){
                //pers have stopped, back to normal time
                timer.setScaleFactor(Timer.NORMAL_SCALE);
            }*/
        } else {
            stop(entity);
        }
    }

    private void stop(Entity entity){
        entity.remove(DirectionComponent.class);
    }

    private boolean canMakeStep(PositionComponent position, VelocityComponent velocity, float delta){
        int nextCellx = (int)(position.x + velocity.x*delta);
        int nextCelly = (int)(position.y + velocity.y*delta);


        //is there any entity blocking pass
        ImmutableArray<Entity> impassableObjects = getEngine().getEntitiesFor(Family.all(ImpassableComponent.class).get());
        for (int i = 0; i < impassableObjects.size(); i++) {
            if (pm.get(impassableObjects.get(i)).x == nextCellx &&
                    pm.get(impassableObjects.get(i)).y == nextCelly){
                return false;
            }
        }

        //if there is no blocking entities check cell
        return GameModel.instance.getCurrentMap().getCell(nextCellx, nextCelly).isWalkable();
    }
}
