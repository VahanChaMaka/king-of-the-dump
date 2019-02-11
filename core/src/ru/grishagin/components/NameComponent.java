package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

public class NameComponent implements Component {
    public String name;

    public NameComponent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
