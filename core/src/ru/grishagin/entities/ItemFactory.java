package ru.grishagin.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectMap;
import ru.grishagin.components.DescriptionComponent;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.items.ConsumableComponent;
import ru.grishagin.components.items.WeightComponent;
import ru.grishagin.utils.AssetManager;

import java.util.Map;

public class ItemFactory {
    private static final String ITEMS = "properties/items.json";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "descripton";
    private static final String WEIGHT = "weight";
    private static final String CONSUMABLE = "consumable";

    public static Entity getItem(int id){
        Map<String, Map<String, Object>> items = AssetManager.instance.readFromJson(ITEMS);
        Map<String, Object> rawItem = items.get(String.valueOf(id));

        Entity newItem = null;
        try {
            newItem = new Entity();
            for (Map.Entry<String, Object> rawComponents : rawItem.entrySet()) {
                newItem.add(makeComponent(rawComponents.getKey(), rawComponents.getValue()));
            }
        } catch (Exception e){
            System.out.println("Warning! Couldn't parse item: " + e.getMessage());
        }
        return newItem;
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
            default:
                throw new Exception("Loading item's components failed!");
        }
    }

}
