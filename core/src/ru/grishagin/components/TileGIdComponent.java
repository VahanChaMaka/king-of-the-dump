package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

public class TileGIdComponent implements Component {
    public final int gid;

    public TileGIdComponent(int gid) {
        this.gid = gid;
    }
}
