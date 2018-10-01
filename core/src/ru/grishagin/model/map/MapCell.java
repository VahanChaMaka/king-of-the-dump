package ru.grishagin.model.map;

public class MapCell {
    int id;
    int typeId;

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
}
