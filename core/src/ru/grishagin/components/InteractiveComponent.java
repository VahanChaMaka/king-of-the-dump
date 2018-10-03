package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

public class InteractiveComponent implements Component {
    public boolean isActive = false;
    public int range = 1; //interact standing on the next cell by default
}
