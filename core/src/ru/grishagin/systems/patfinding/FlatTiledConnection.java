package ru.grishagin.systems.patfinding;

import com.badlogic.gdx.ai.pfa.DefaultConnection;

public class FlatTiledConnection extends DefaultConnection<FlatTiledNode> {

    static final float NON_DIAGONAL_COST = (float)Math.sqrt(2);

    FlatTiledGraph worldMap;

    public FlatTiledConnection (FlatTiledGraph worldMap, FlatTiledNode fromNode, FlatTiledNode toNode) {
        super(fromNode, toNode);
        this.worldMap = worldMap;
    }


    //I have no idea what's going on here)
    @Override
    public float getCost () {
        //if (worldMap.diagonal) return 1;
        //return getToNode().x != worldMap.getStartNode().x && getToNode().y != worldMap.getStartNode().y ? NON_DIAGONAL_COST : 1;
        return NON_DIAGONAL_COST;
    }
}
