package ru.grishagin.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectMap;
import ru.grishagin.components.DescriptionComponent;
import ru.grishagin.components.NameComponent;
import ru.grishagin.components.TypeIdComponent;
import ru.grishagin.components.items.ConsumableComponent;
import ru.grishagin.components.items.WeightComponent;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.ComponentNotFoundException;
import ru.grishagin.utils.ObjectNotFoundException;

import java.util.Map;

public class ItemFactory {
    private static final String ITEMS = "properties/items.json";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String WEIGHT = "weight";
    private static final String CONSUMABLE = "consumable";

    public static Entity getItem(int id){
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
            for (Map.Entry<String, Object> rawComponents : rawItem.entrySet()) {
                newItem.add(makeComponent(rawComponents.getKey(), rawComponents.getValue()));
            }
            newItem.add(new TypeIdComponent(id));
        } catch (Exception e){
            System.out.println("Warning! Couldn't parse item: " + e.getMessage());
        }
        return newItem;
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
            default:
                throw new ComponentNotFoundException("Loading item's components failed! Couldn't load component "
                        + componentName);
        }
    }

}
