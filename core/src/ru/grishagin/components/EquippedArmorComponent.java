package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.items.ArmorComponent;
import ru.grishagin.utils.Logger;

public class EquippedArmorComponent implements Component {
    private Entity suit;
    private Entity head;

    private Entity defaultSuit;
    private Entity defaultHead;

    public EquippedArmorComponent(){
        defaultSuit = new Entity();
        defaultSuit.add(new ArmorComponent(ArmorComponent.ArmorType.SUIT, 0, 0));

        defaultHead = new Entity();
        defaultHead.add(new ArmorComponent(ArmorComponent.ArmorType.HEAD, 0, 0));
    }

    public EquippedArmorComponent(Entity defaultSuit, Entity defaultHead) {
        this.defaultSuit = defaultSuit;
        this.defaultHead = defaultHead;
    }

    public Entity getSuit() {
        if(suit != null){
            return suit;
        } else {
            return defaultSuit;
        }
    }

    public void changeSuit(Entity suit) {
        if(suit.getComponent(ArmorComponent.class).type == ArmorComponent.ArmorType.SUIT) {
            this.suit = suit;
        } else {
            Logger.warning("Trying to wear head armor as suit!");
        }
    }

    public Entity getHead() {
        if(head != null) {
            return head;
        } else {
            return defaultHead;
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
