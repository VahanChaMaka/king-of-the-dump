package ru.grishagin.systems.patfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public abstract class TiledNode<N extends TiledNode<N>> {

    public final static int CONNECTION_CAPACITY = 4;

    /** The x coordinate of this tile */
    public final int x;

    /** The y coordinate of this tile */
    public final int y;

    private TileNodeType type;

    protected Array<Connection<N>> connections;

    public TiledNode (int x, int y, TileNodeType type, Array<Connection<N>> connections) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.connections = connections;
    }

    public abstract int getIndex ();

    public TileNodeType getType() {
        return type;
    }

    void setType(TileNodeType type) {
        this.type = type;
    }

    public Array<Connection<N>> getConnections () {
        return this.connections;
    }

}
