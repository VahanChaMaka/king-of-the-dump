package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

public class LockedComponent implements Component {
    //TODO: add specific key to unlock
    //TODO: add lock complication

    public boolean isLocked;

    public LockedComponent() {
        isLocked = false;
    }

    public LockedComponent(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
