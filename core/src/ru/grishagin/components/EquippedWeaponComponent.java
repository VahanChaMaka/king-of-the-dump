package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class EquippedWeaponComponent implements Component {
    public Entity weapon;
    public float lastAttack; //time past since last attack

    public EquippedWeaponComponent(Entity defaultWeapon) {
        this.weapon = defaultWeapon;
    }
}
