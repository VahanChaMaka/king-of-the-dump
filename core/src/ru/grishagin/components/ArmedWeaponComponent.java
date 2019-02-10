package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class ArmedWeaponComponent implements Component {
    public final Entity defaultWeapon;
    public Entity activeWeapon;
    public float lastAttack; //time past since last attack

    public ArmedWeaponComponent(Entity defaultWeapon) {
        this.defaultWeapon = defaultWeapon;
    }

    public void changeWeapon(Entity weapon){
        if(weapon != null) {
            activeWeapon = weapon;
        } else {
            activeWeapon = defaultWeapon;
        }
    }
}
