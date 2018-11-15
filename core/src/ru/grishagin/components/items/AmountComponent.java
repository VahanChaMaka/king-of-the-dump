package ru.grishagin.components.items;

import com.badlogic.ashley.core.Component;

public class AmountComponent implements Component {
    public int amount;

    public AmountComponent(int amount) {
        this.amount = amount;
    }
}
