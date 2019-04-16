package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

import java.util.Map;

public class ShaderComponent implements Component {
    public static final String COLOR = "color";

    //default shader should be null
    public ShaderType shaderType = null;
    public Map<String, Object> shaderProperties;
    public boolean isActive = false;

    public ShaderComponent(ShaderType shaderType) {
        this.shaderType = shaderType;
    }

    public ShaderComponent(ShaderType shaderType, Map<String, Object> shaderProperties) {
        this.shaderType = shaderType;
        this.shaderProperties = shaderProperties;
    }
}
