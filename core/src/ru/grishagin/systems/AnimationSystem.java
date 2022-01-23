package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ru.grishagin.components.*;
import ru.grishagin.model.GameModel;
import ru.grishagin.model.map.TiledMapHelper;
import ru.grishagin.model.messages.MessageType;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.Logger;

public class AnimationSystem extends IteratingSystem implements Telegraph {
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<TypeIdComponent> tm = ComponentMapper.getFor(TypeIdComponent.class);
    private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);

    public AnimationSystem() {
        super(Family.all(AnimationComponent.class,
                        OrientationComponent.class)
                .get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //play animation here
        AnimationComponent animationComponent = am.get(entity);
        SpriteComponent spriteComponent = sm.get(entity);

        Sprite nextFrame = new Sprite(animationComponent.runningAnimation.getKeyFrame(animationComponent.timer));

        if(spriteComponent != null) {
            animationComponent.timer += deltaTime;
            //not the greatest idea to create a new sprite every time
            float xPos = spriteComponent.sprite.getX();
            float yPos = spriteComponent.sprite.getY();

            spriteComponent.sprite = nextFrame;
            //set new frame sprite position same as old
            spriteComponent.sprite.setX(xPos);
            spriteComponent.sprite.setY(yPos);
        } else{
            entity.add(new SpriteComponent(nextFrame));
        }
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        if(msg.extraInfo != null){
            Entity entity = (Entity) msg.extraInfo;

            if(msg.message == MessageType.START_MVMNT || msg.message == MessageType.ORIENTATION_CHANGE){
                int typeId = entity.getComponent(TypeIdComponent.class).id;
                OrientationComponent.Orientation orientation = entity.getComponent(OrientationComponent.class).orientation;
                entity.add(new AnimationComponent(AssetManager.instance.getNPCAnimation(typeId, AssetManager.WALKING, orientation)));
            } else if(msg.message == MessageType.STOP_MVMNT) {
                int typeId = entity.getComponent(TypeIdComponent.class).id;
                OrientationComponent.Orientation orientation = entity.getComponent(OrientationComponent.class).orientation;
                entity.add(new AnimationComponent(AssetManager.instance.getNPCAnimation(typeId, AssetManager.IDLE, orientation)));
            } else {
                changeSprite(entity, msg.message);
            }
        }
        return true;
    }

    public void changeSprite(Entity entity, int eventType){
        NextStatesIds states = entity.getComponent(NextStatesIds.class);
        switch (eventType){
            case MessageType.DEATH:
                //remove animation to set static dead sprite
                if(am.has(entity)){
                    entity.remove(AnimationComponent.class);
                }
                sm.get(entity).sprite = new Sprite(AssetManager.instance.getNPCImage(tm.get(entity).id, AssetManager.DEAD));
                Logger.info(entity.getComponent(NameComponent.class).name + "'s sprite changed to another");
                break;
            case MessageType.CLOSED:
                sm.get(entity).sprite = new Sprite(TiledMapHelper.getStateTextureRegion(GameModel.instance.getCurrentMap().getMap(),
                        states.states.get("closed"), entity));
                break;
            case MessageType.OPENED:
                sm.get(entity).sprite = new Sprite(TiledMapHelper.getStateTextureRegion(GameModel.instance.getCurrentMap().getMap(),
                        states.states.get("opened"), entity));
                break;
            default:
                Logger.info("Can't change sprite for event '" + eventType + "'!");
        }
    }
}
