package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.EquippedArmorComponent;
import ru.grishagin.components.EquippedWeaponComponent;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.components.items.AmountComponent;
import ru.grishagin.components.items.ArmorComponent;
import ru.grishagin.components.items.OwnerComponent;
import ru.grishagin.components.items.WeaponComponent;
import ru.grishagin.components.tags.PlayerControlled;
import ru.grishagin.entities.ItemFactory;
import ru.grishagin.model.GameModel;
import ru.grishagin.utils.Logger;

import java.util.List;

public class InventorySystem extends IteratingSystem {
    private ComponentMapper<InventoryComponent> im = ComponentMapper.getFor(InventoryComponent.class);
    private ComponentMapper<TypeIdComponent> tm = ComponentMapper.getFor(TypeIdComponent.class);
    private ComponentMapper<AmountComponent> am = ComponentMapper.getFor(AmountComponent.class);

    public InventorySystem() {
        super(Family.all(InventoryComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //TODO: update items in each inventory
    }

    public void addItem(Entity target, Entity item){
        List<Entity> items = im.get(target).items;

        //if item is present, increase amount
        for (int i = 0; i < items.size(); i++) {
            if(tm.get(items.get(i)).id == tm.get(item).id){
                am.get(items.get(i)).amount += am.get(item).amount;
                return;
            }
        }

        //if there is no such item add it
        items.add(item);
        item.add(new OwnerComponent(target));
    }

    public void destroyItem(Entity target){

    }

    public void transferItem(Entity source, Entity target, int itemId, int amount){
        InventoryComponent sourceInventory = im.get(source);

        for (Entity item : sourceInventory.items) {
            if(tm.get(item).id == itemId){
                addItem(target, item);
                deleteItem(source, itemId, amount);
                break;
            }
        }
    }

    public void transferItem(Entity source, Entity target, int itemId){
        transferItem(source, target, itemId, 1);
    }

    //delete item completely. Not destroy, not throw out. Just delete
    public void deleteItem(Entity owner, int id, int amount){
        List<Entity> items = im.get(owner).items;
        for (int i = 0; i < items.size(); i++) {
            if(tm.get(items.get(i)).id == id){
                int presentAmount = am.get(items.get(i)).amount;
                if(presentAmount > amount){ //if items in inventory is more than to remove, just change amount
                    am.get(items.get(i)).amount = presentAmount - amount;
                } else { //else remove item completely
                    items.remove(i);

                    if (presentAmount - amount < 0){
                        System.out.println("Warning! Wrong amount of items to remove, expect more errors!");
                    }
                }

                break;
            }
        }
    }

    public void equipItem(Entity item){
        Entity owner = item.getComponent(OwnerComponent.class).owner;
        final boolean isOwnerByPlayer = owner.getComponent(PlayerControlled.class) != null;
        Entity player = GameModel.instance.getPlayer();
        EquippedArmorComponent equippedArmorComponent = player.getComponent(EquippedArmorComponent.class);
        EquippedWeaponComponent equippedWeaponComponent = player.getComponent(EquippedWeaponComponent.class);

        //remove item from inventory
        int itemId = item.getComponent(TypeIdComponent.class).id;
        deleteItem(owner, itemId, 1);

        if (!isOwnerByPlayer) {
            //TODO: transfer to inventory
        }

        ArmorComponent armorToEquip = item.getComponent(ArmorComponent.class);
        if (armorToEquip != null) {
            if (armorToEquip.type == ArmorComponent.ArmorType.HEAD) {
                equippedArmorComponent.changeHead(item);
            } else if (armorToEquip.type == ArmorComponent.ArmorType.SUIT) {
                equippedArmorComponent.changeSuit(item);
            } else {
                Logger.warning(item, "Trying to equip unknown type of armor!");
            }
            return;
        }

        WeaponComponent weaponToEquip = item.getComponent(WeaponComponent.class);
        if (weaponToEquip != null) {
            equippedWeaponComponent.weapon = item;
        }
    }

    public void takeOffItem(Entity item){
        Entity player = GameModel.instance.getPlayer();
        EquippedArmorComponent equippedArmorComponent = player.getComponent(EquippedArmorComponent.class);
        EquippedWeaponComponent equippedWeaponComponent = player.getComponent(EquippedWeaponComponent.class);

        if(equippedArmorComponent.getHead() == item){
            equippedArmorComponent.changeHead(new Entity().add(new ArmorComponent(ArmorComponent.ArmorType.HEAD, 0, 0)));
        } else if(equippedArmorComponent.getSuit() == item){
            equippedArmorComponent.changeHead(new Entity().add(new ArmorComponent(ArmorComponent.ArmorType.SUIT, 0, 0)));
        } else if(equippedWeaponComponent.weapon == item){
            equippedWeaponComponent.weapon = ItemFactory.getDefaultPlayerWeapon();
        }

        //return item to inventory
        addItem(player, item);
    }
}
