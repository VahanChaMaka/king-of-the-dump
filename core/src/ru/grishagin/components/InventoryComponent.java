package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.LinkedList;
import java.util.List;

public class InventoryComponent implements Component {
    List<Entity> items = new LinkedList<>();
}
