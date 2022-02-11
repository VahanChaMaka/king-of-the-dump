package ru.grishagin.components.items;

import com.badlogic.ashley.core.Component;

public class WeaponComponent implements Component {
    public final Type type;
    public final DamageType damageType;
    public int damage;
    public int range; //in cells
    public int speed; //in seconds

    public WeaponComponent(Type type, DamageType damageType, int damage, int range, int speed) {
        this.type = type;
        this.damageType = damageType;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
    }

    public enum DamageType {
        MELEE,
        FIREARM
    }

    public enum Type {
        HANDS,
        KNIFE,
        PISTOL
    }
}
