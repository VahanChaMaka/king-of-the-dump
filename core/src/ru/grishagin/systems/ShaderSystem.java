package ru.grishagin.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import ru.grishagin.components.InteractiveComponent;
import ru.grishagin.components.ShaderComponent;

import java.util.Collections;

public class ShaderSystem extends EntitySystem implements Telegraph {

    //subscription on the DEATH events
    @Override
    public boolean handleMessage(Telegram msg) {
        Entity entity = (Entity)msg.extraInfo;//dead entity
        if(entity.getComponent(InteractiveComponent.class) != null){
            ShaderComponent shader = entity.getComponent(ShaderComponent.class);
            if(shader != null){
                shader.shaderProperties = Collections.singletonMap(ShaderComponent.COLOR, Color.YELLOW);
            }
        } else {
            entity.remove(ShaderComponent.class);
        }
        return true;
    }
}
