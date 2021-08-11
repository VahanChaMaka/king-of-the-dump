package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

public class OrientationComponent implements Component {

    public Orientation orientation;

    public OrientationComponent(Orientation orientation) {
        this.orientation = orientation;
    }

    public enum Orientation{
        N,
        NE,
        E,
        SE,
        S,
        SW,
        W,
        NW
    }
}
