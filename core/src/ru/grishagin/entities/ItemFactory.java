package ru.grishagin.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.DescriptionComponent;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.SoundComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.components.items.*;
import ru.grishagin.model.GameModel;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.ComponentNotFoundException;
import ru.grishagin.utils.ObjectNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ru.grishagin.utils.AssetManager.ITEMS;

public class ItemFactory {
    private static final String NAME = "name";
    public static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String WEIGHT = "weight";
    private static final String CONSUMABLE = "consumable";
    private static final String COMPONENTS = "components";
    private static final String DESTROYABLE = "destroyable";
    private static final String DESTROYABLE_MANUALLY = "destroyableManually";
    public static final String AMOUNT = "amount";
    public static final String TYPE = "type";
    public static final String WEAPON = "weapon";
    public static final String WEAPON_DAMAGE = "damage";
    public static final String WEAPON_SPEED = "speed";
    public static final String WEAPON_RANGE = "range";
    public static final String ARMOR = "armor";
    public static final String MELEE_RESISTANCE = "melee";
    public static final String FIREARM_RESISTANCE = "firearm";


    public static final String SOUND = "sound";
    public static final String ATTACK = "attack";

    public static Entity getItem(int id){
        return getItem(id, 1);
    }

    public static Entity getItem(int id, int amount){
        Map<String, Map<String, Object>> items = AssetManager.instance.readFromJson(ITEMS);
        Map<String, Object> rawItem = null;
        try {
            rawItem = getRawObject(items, String.valueOf(id));
        } catch (ObjectNotFoundException e) {
            System.out.println("Error! Couldn't load object with id = " + id);
            return null;
        }

        Entity newItem = null;
        try {
            newItem = new Entity();
            newItem.add(new TypeIdComponent(id));
            newItem.add(new AmountComponent(amount));
            for (Map.Entry<String, Object> rawComponents : rawItem.entrySet()) {
                Component component = makeComponent(rawComponents.getKey(), rawComponents.getValue());
                if(component != null) {
                    newItem.add(component);
                }
            }
        } catch (Exception e){
            System.out.println("Warning! Couldn't parse item: " + e.getMessage());
        }

        GameModel.instance.engine.addEntity(newItem);

        return newItem;
    }

    public static List<Map<String, Object>> getDestroyResult(int id){
        Map<String, Map<String, Object>> items = AssetManager.instance.readFromJson(ITEMS);
        try {
            Map<String, Object> rawItem = null;
            rawItem = getRawObject(items, String.valueOf(id));
            return (List<Map<String, Object>>)((Map)rawItem.get(DESTROYABLE)).get(COMPONENTS);
        } catch (ObjectNotFoundException e) {
            System.out.println("Error! Couldn't load object with id = " + id);
            return null;
        }
    }

    public static String getItemName(int id){
        Map<String, Map<String, Object>> items = AssetManager.instance.readFromJson(ITEMS);
        try {
            Map<String, Object> rawItem = null;
            return (String)getRawObject(items, String.valueOf(id)).get(NAME);
        } catch (ObjectNotFoundException e) {
            System.out.println("Error! Couldn't load object with id = " + id);
            return null;
        }
    }

    private static Map<String, Object> getRawObject(Map<String, Map<String, Object>> items, String id) throws ObjectNotFoundException {
        Map<String, Object> rawItem = items.get(String.valueOf(id));

        if(rawItem == null ){
            throw new ObjectNotFoundException();
        }

        return rawItem;
    }

    private static Component makeComponent(String componentName, Object rawComponentData) throws Exception{
        switch (componentName){
            case NAME:
                return new NameComponent((String) rawComponentData);
            case DESCRIPTION:
                return new DescriptionComponent((String) rawComponentData);
            case WEIGHT:
                return new WeightComponent((Integer) rawComponentData);
            case CONSUMABLE:
                return new ConsumableComponent();
            case DESTROYABLE:
                return new DestroyableComponent(((Map)rawComponentData).containsKey(DESTROYABLE_MANUALLY));
            case WEAPON:
                Map<String, Object> weaponData = (Map<String, Object>)rawComponentData;
                return new WeaponComponent(WeaponComponent.DamageType.valueOf(((String)weaponData.get(TYPE)).toUpperCase()),
                        (int)weaponData.get(WEAPON_DAMAGE),
                        (int)weaponData.get(WEAPON_RANGE),
                        (int)weaponData.get(WEAPON_SPEED));
            case ARMOR:
                Map<String, Object> armorData = (Map<String, Object>)rawComponentData;
                return new ArmorComponent(ArmorComponent.ArmorType.valueOf(((String)armorData.get(TYPE)).toUpperCase()),
                        (int)armorData.get(MELEE_RESISTANCE),
                        (int)armorData.get(FIREARM_RESISTANCE));

            case SOUND:
                Map<String, String> soundData =(Map<String, String>)rawComponentData;
                return new SoundComponent(soundData);
            default:
                throw new ComponentNotFoundException("Loading item's components failed! Couldn't load component: \""
                        + componentName + "\"");
        }
    }

    public static Entity getDefaultPlayerWeapon(){
        return ItemFactory.getItem(301);
    }

}
