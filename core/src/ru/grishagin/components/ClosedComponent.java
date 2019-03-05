package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

public class ClosedComponent implements Component {
    public boolean isClosed;

    public ClosedComponent(boolean isClosed) {
        this.isClosed = isClosed;
    }
}
