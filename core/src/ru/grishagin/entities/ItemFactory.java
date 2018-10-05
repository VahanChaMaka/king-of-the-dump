package ru.grishagin.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectMap;
import ru.grishagin.components.NameComponent;
import ru.grishagin.utils.AssetManager;

import java.util.Map;

public class ItemFactory {
    private static final String ITEMS = "properties/items.json";
    private static final String NAME = "name";

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
            default:
                throw new Exception("Loading item's components failed!");
        }
    }

}
