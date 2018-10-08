package ru.grishagin.components.items;

import com.badlogic.ashley.core.Component;

//weight of one item not pack of them
public class WeightComponent implements Component {
    public int weight;

    public WeightComponent(int weight) {
        this.weight = weight;
    }
}
