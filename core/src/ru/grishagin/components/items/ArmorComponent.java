package ru.grishagin.components.items;

import com.badlogic.ashley.core.Component;

public class ArmorComponent implements Component {
    public final ArmorType type;
    public int meleeResistance;
    public int firearmResistance;

    public ArmorComponent(ArmorType type, int meleeResistance, int firearmResistance) {
        this.type = type;
        this.meleeResistance = meleeResistance;
        this.firearmResistance = firearmResistance;
    }

    public enum ArmorType{
        SUIT,
        HEAD
    }
}
