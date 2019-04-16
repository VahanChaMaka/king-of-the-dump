package ru.grishagin.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.grishagin.components.ShaderComponent;
import ru.grishagin.components.SpriteComponent;

import java.util.Map;

public class ShaderHelper {

    public static ShaderProgram getShader(ShaderComponent shaderComponent, SpriteComponent spriteComponent){
        ShaderProgram shader = AssetManager.instance.getShader(shaderComponent.shaderType);

        //TODO: properties is empty now
        //if(shaderComponent.shaderProperties != null && !shaderComponent.shaderProperties.isEmpty()) {
            shader.begin();
            setupProperties(shader, shaderComponent.shaderProperties, spriteComponent);
            shader.end();
        //}

        return shader;
    }

    //TODO: rewrite in generalized way, not only for outline
    private static void setupProperties(ShaderProgram shader, Map<String, Object> properties, SpriteComponent spriteComponent){
        /*for (Map.Entry<String, Object> shaderProperty : shaderComponent.shaderProperties.entrySet()) {
            shader.setUniformf(shaderProperty.getKey(), shaderProperty.getValue());
        }*/
        shader.setUniformf("u_viewportInverse", new Vector2(1f / spriteComponent.sprite.getTexture().getWidth(),
                1f / spriteComponent.sprite.getTexture().getHeight()));
        shader.setUniformf("u_offset", 1f);
        shader.setUniformf("u_step", Math.min(1f, spriteComponent.sprite.getTexture().getWidth() / 70f));
        if(properties.containsKey(ShaderComponent.COLOR)) {
            shader.setUniformf("u_color", ((Color)properties.get(ShaderComponent.COLOR)));
            //shader.setUniformf("u_color", new Vector3(1f, 0, 0));
        } else {
            Logger.warning("Shader's color is not defined!");
            shader.setUniformf("u_color", new Vector3(1f, 1f, 1f));
        }
    }
}
