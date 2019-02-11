package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class EquippedWeaponComponent implements Component {
    public final Entity defaultWeapon;
    public Entity activeWeapon;
    public float lastAttack; //time past since last attack

    public EquippedWeaponComponent(Entity defaultWeapon) {
        this.defaultWeapon = defaultWeapon;
        this.activeWeapon = defaultWeapon;
    }

    public void changeWeapon(Entity weapon){
        if(weapon != null) {
            activeWeapon = weapon;
        } else {
            activeWeapon = defaultWeapon;
        }
    }
}
