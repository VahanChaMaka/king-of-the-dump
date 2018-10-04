package ru.grishagin.model.actions;

import com.badlogic.ashley.core.Entity;

public interface Action {

    //source is initiator of action (player for example)
    void execute(Entity source, Entity target);
}
