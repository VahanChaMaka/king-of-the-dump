package ru.grishagin.systems.patfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class FlatTiledNode extends TiledNode<FlatTiledNode> {

    private final int id;

    public FlatTiledNode(int x, int y, TileNodeType type, int id) {
        super(x, y, type, new Array<Connection<FlatTiledNode>>(CONNECTION_CAPACITY));
        this.id = id;
    }

    @Override
    public int getIndex() {
        return id;
    }
}
