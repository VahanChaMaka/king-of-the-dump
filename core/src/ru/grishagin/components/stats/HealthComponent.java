package ru.grishagin.components.stats;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
    public int maxHealth;
    public int health;

    //init with full health
    public HealthComponent(int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }
}
