package ru.grishagin.model.map;

import ru.grishagin.utils.AssetManager;
import java.util.Map;

public class MapCell {
    private int id;
    private int typeId;

    public MapCell(int id, int typeId) {
        this.id = id;
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public int getTypeId() {
        return typeId;
    }

    public boolean isWalkable(){
        Map<String, Map<String, Object>> rawCells = AssetManager.instance.readFromJson("properties/tiles.json");
        Map<String, Object> rawCell = rawCells.get(String.valueOf(typeId));
        if(rawCell == null || rawCell.get("walkable") == null){
            return true;
        } else {
            return (Boolean) rawCell.get("walkable");
        }
    }
}
