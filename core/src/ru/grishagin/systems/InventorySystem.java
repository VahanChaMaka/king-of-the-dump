package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.InventoryComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.components.items.AmountComponent;

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
    public void deleteItem(Entity source, int id, int amount){
        List<Entity> items = im.get(source).items;
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
}
