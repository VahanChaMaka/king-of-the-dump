package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

public class TypeIdComponent implements Component {

    public TypeIdComponent(int id) {
        this.id = id;
    }

    public int id;
}
