package ru.grishagin.components.items;

import com.badlogic.ashley.core.Component;

public class DestroyableComponent implements Component {
    public boolean isDestroyableManually = false;

    public DestroyableComponent(boolean isDestroyableManually) {
        this.isDestroyableManually = isDestroyableManually;
    }

    public DestroyableComponent() {
    }
}
