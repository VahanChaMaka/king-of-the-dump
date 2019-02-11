package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import ru.grishagin.model.actions.Action;

public class InteractiveComponent implements Component {
    public Action action;
    public int range = 1; //interact standing on the next cell by default

    public InteractiveComponent(Action action) {
        this.action = action;
    }

    public InteractiveComponent(Action action, int range) {
        this.action = action;
        this.range = range;
    }
}
