package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.audio.Sound;
import ru.grishagin.components.SoundComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.Logger;

public class SoundSystem extends EntitySystem implements Telegraph {
    private ComponentMapper<SoundComponent> sm = ComponentMapper.getFor(SoundComponent.class);

    @Override
    public boolean handleMessage(Telegram msg) {
        if(msg.extraInfo != null){
            playSound((Entity)msg.extraInfo, msg.message);
        }
        return true;
    }

    private void playSound(Entity entity, int eventType){
        SoundComponent soundComponent = sm.get(entity);
        if(soundComponent != null){
            if(soundComponent.get(eventType) != null){
                Sound sound = AssetManager.instance.getSound(soundComponent.get(eventType));
                sound.play();
            } else {
                Logger.info(entity, "it has no sound to play for event " + eventType);
            }
        } else {
            Logger.info(entity, " has no sounds to play at all");
        }
    }
}
