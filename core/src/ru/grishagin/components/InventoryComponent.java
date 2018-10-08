package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.LinkedList;
import java.util.List;

public class InventoryComponent implements Component {
    public List<Entity> items = new LinkedList<>();
    public int maxWeight;

    public InventoryComponent(int maxWeight) {
        this.maxWeight = maxWeight;
    }
}
