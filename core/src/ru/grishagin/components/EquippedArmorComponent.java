package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.items.ArmorComponent;
import ru.grishagin.utils.Logger;

public class EquippedArmorComponent implements Component {
    private Entity suit;
    private Entity head;

    public EquippedArmorComponent(){
        suit = new Entity();
        suit.add(new ArmorComponent(ArmorComponent.ArmorType.SUIT, 0, 0));

        head = new Entity();
        head.add(new ArmorComponent(ArmorComponent.ArmorType.HEAD, 0, 0));
    }

    public EquippedArmorComponent(Entity suit, Entity head) {
        this.suit = suit;
        this.head = head;
    }

    public Entity getSuit() {
        return suit;
    }

    public Entity getHead() {
        return head;
    }

    public void changeSuit(Entity suit) {
        if(suit.getComponent(ArmorComponent.class).type == ArmorComponent.ArmorType.SUIT) {
            this.suit = suit;
        } else {
            Logger.warning("Trying to wear head armor as suit!");
        }
    }

    public void changeHead(Entity head) {
        if(head.getComponent(ArmorComponent.class).type == ArmorComponent.ArmorType.HEAD) {
            this.head = head;
        } else {
            Logger.warning("Trying to put a suit on the head!");
        }
    }
}
