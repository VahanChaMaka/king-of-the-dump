package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.SpriteComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.model.messages.MessageType;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.Logger;

public class AnimationSystem extends IteratingSystem implements Telegraph {
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<TypeIdComponent> tm = ComponentMapper.getFor(TypeIdComponent.class);

    public AnimationSystem() {
        super(Family.all(SpriteComponent.class).get()); //TODO: add animation component
    }

    @Override
    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        //play animation here
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        if(msg.extraInfo != null){
            changeSprite((Entity)msg.extraInfo, msg.message);
        }
        return true;
    }

    public void changeSprite(Entity entity, int eventType){
        switch (eventType){
            case MessageType.DEATH:
                sm.get(entity).sprite = new Sprite(AssetManager.instance.getNPCImage(tm.get(entity).id, AssetManager.DEAD));
                Logger.info(entity.getComponent(NameComponent.class).name + "'s sprite changed to another");
                break;
            case MessageType.CLOSED:
                System.out.println(sm.get(entity).sprite);
                break;
            default:
                Logger.info("Can't change sprite for event '" + eventType + "'!");
        }
    }
}
