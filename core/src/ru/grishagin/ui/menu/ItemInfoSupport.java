package ru.grishagin.ui.menu;

import com.badlogic.ashley.core.Entity;

public interface ItemInfoSupport {
    public void showInfo(Entity item, Entity owner, Entity target);
    public void update();
}
