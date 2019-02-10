package ru.grishagin.components.items;

import com.badlogic.ashley.core.Component;

public class WeaponComponent implements Component {
    public int damage;
    public int range; //in cells
    public int speed; //in seconds

    public WeaponComponent(int damage, int range, int speed) {
        this.damage = damage;
        this.range = range;
        this.speed = speed;
    }
}
