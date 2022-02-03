package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import ru.grishagin.components.*;
import ru.grishagin.model.GameModel;
import ru.grishagin.model.map.TiledMapHelper;
import ru.grishagin.model.messages.MessageType;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.Logger;

import java.util.HashMap;
import java.util.Map;

public class AnimationSystem extends IteratingSystem implements Telegraph {
    public static final Vector2 FRAME_OFFSET= new Vector2(-24, 0);

    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<TypeIdComponent> tm = ComponentMapper.getFor(TypeIdComponent.class);
    private ComponentMapper<AnimationComponent> am =
            ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<IdComponent> im = ComponentMapper.getFor(IdComponent.class);

    //entity id to message type
    private Map<Integer, Integer> animationStates = new HashMap<>();

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
        IdComponent entityId = im.get(entity);

        //if attack animation playing, change to idle animation when attack animation is finished
        if (animationStates.get(entityId.id) != null && animationStates.get(entityId.id) == MessageType.ATTACK) {
            if(animationComponent.runningAnimation.isAnimationFinished(animationComponent.timer)) {
                //it will set IDLE animation
                MessageManager.getInstance().dispatchMessage(MessageType.STOP_MVMNT, entity);
                animationStates.remove(entityId.id);
            }
        }

        Sprite nextFrame = new Sprite(
                animationComponent.runningAnimation.getKeyFrame(animationComponent.timer));

        if (spriteComponent != null) {
            animationComponent.timer += deltaTime;
            //not the greatest idea to create a new sprite every time
            float xPos = spriteComponent.sprite.getX();
            float yPos = spriteComponent.sprite.getY();

            spriteComponent.sprite = nextFrame;
            //set new frame sprite position same as old
            spriteComponent.sprite.setX(xPos);
            spriteComponent.sprite.setY(yPos);
        } else {
            SpriteComponent newComponent = new SpriteComponent(nextFrame);
            newComponent.offset = FRAME_OFFSET;
            entity.add(newComponent);
        }
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        if (msg.extraInfo != null) {
            Entity entity = (Entity) msg.extraInfo;
            int entityId = entity.getComponent(IdComponent.class).id;
            TypeIdComponent typeIdComponent = tm.get(entity);
            OrientationComponent orientationComponent =
                    entity.getComponent(OrientationComponent.class);

            if (typeIdComponent != null && orientationComponent != null) {
                int typeId = typeIdComponent.id;
                OrientationComponent.Orientation orientation = orientationComponent.orientation;
                if (msg.message == MessageType.START_MVMNT) {
                    setAnimation(entity, typeId, AssetManager.WALKING, orientation);
                } else if (msg.message == MessageType.ORIENTATION_CHANGE) {
                    var v = entity.getComponent(VelocityComponent.class);
                    if (v.x == 0 & v.y == 0) {
                        setAnimation(entity, typeId, AssetManager.IDLE, orientation);
                    } else {
                        setAnimation(entity, typeId, AssetManager.WALKING, orientation);
                    }
                } else if (msg.message == MessageType.STOP_MVMNT) {
                    setAnimation(entity, typeId, AssetManager.IDLE, orientation);
                } else if (msg.message == MessageType.DEATH) {
                    entity.add(new AnimationComponent(
                            AssetManager.instance.getNPCAnimation(typeId, AssetManager.DEAD,
                                    orientation), Animation.PlayMode.NORMAL));
                    animationStates.remove(entityId);
                } else if (msg.message == MessageType.ATTACK) {
                    AnimationComponent animation = new AnimationComponent(
                            AssetManager.instance.getNPCAnimation(typeId, AssetManager.ATTACK_1,
                                    orientation), Animation.PlayMode.NORMAL);
                    animation.isBlocking = true;
                    entity.add(animation);
                    //put to the map to replace with idle animation later
                    animationStates.put(entityId, msg.message);
                } else {
                    changeSprite(entity, msg.message);
                }
            } else {
                changeSprite(entity, msg.message);
            }
        }
        return true;
    }

    public void changeSprite(Entity entity, int eventType) {
        NextStatesIds states = entity.getComponent(NextStatesIds.class);
        switch (eventType) {
            case MessageType.CLOSED -> sm.get(entity).sprite =
                    new Sprite(TiledMapHelper.getStateTextureRegion(
                            GameModel.instance.getCurrentMap().getMap(),
                            states.states.get("closed"), entity));
            case MessageType.OPENED -> sm.get(entity).sprite =
                    new Sprite(TiledMapHelper.getStateTextureRegion(
                            GameModel.instance.getCurrentMap().getMap(),
                            states.states.get("opened"), entity));
            default -> Logger.info("Can't change sprite for event '" + eventType + "'!");
        }
    }

    private void setAnimation(Entity entity, int typeId, String animationName,
                              OrientationComponent.Orientation orientation) {
        entity.add(new AnimationComponent(
                AssetManager.instance.getNPCAnimation(typeId, animationName, orientation)));
    }
}
