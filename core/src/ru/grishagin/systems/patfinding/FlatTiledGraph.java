package ru.grishagin.systems.patfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import ru.grishagin.model.GameModel;
import ru.grishagin.model.map.MapPropertiesHelper;
import ru.grishagin.model.map.TiledBasedMap;

public class FlatTiledGraph implements TiledGraph<FlatTiledNode> {

    /*
    *Unfold 2D map to 1D array
    *
    * 1 2 3
    * 4 5 6  -----> 1 2 3 4 5 6 7 8 9
    * 7 8 9
    *
     */

    protected Array<FlatTiledNode> nodes;

    private boolean diagonal;
    private int width;
    private int height;

    public FlatTiledGraph () {
        //this.nodes = new Array<FlatTiledNode>(size);
        this.diagonal = false;
    }

    @Override
    public void init(TiledBasedMap map) {
        TiledMapTileLayer layer = ((TiledMapTileLayer)map.getMap().getLayers().get(0));

        width = layer.getWidth();
        height = layer.getHeight();
        nodes = new Array<FlatTiledNode>(height*width);

        //create nodes without connections
        for (int x = 0; x < width; x++) {
            int idx = x * height;
            for (int y = 0; y < height; y++) {
                //resolve types, can be other types in future
                TileNodeType nodeType;
                if(MapPropertiesHelper.isWalkable(map, x, y)){
                    nodeType = TileNodeType.NORMAL;
                } else {
                    nodeType = TileNodeType.IMPASSABLE;
                }

                nodes.add(new FlatTiledNode(x, y, nodeType, idx+y)); //idx + y makes id during transformation 2D array to 1D
            }
        }

        //TODO: can I make nodes and connect it in one loop?
        //make connections between nodes.
        for (int x = 0; x < width; x++) {
            int idx = x * height;
            for (int y = 0; y < height; y++) {
                FlatTiledNode n = nodes.get(idx + y);
                if (x > 0) addConnection(n, -1, 0);
                if (y > 0) addConnection(n, 0, -1);
                if (x < width - 1) addConnection(n, 1, 0);
                if (y < height - 1) addConnection(n, 0, 1);
            }
        }
    }

    @Override
    public FlatTiledNode getNode(int x, int y) {
        //may be (x * width + y)????
        return nodes.get(x * height + y);
    }

    @Override
    public FlatTiledNode getNode (int index) {
        return nodes.get(index);
    }

    @Override
    public int getIndex (FlatTiledNode node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<FlatTiledNode>> getConnections(FlatTiledNode fromNode) {
        return fromNode.getConnections();
    }

    private void addConnection (FlatTiledNode n, int xOffset, int yOffset) {
        FlatTiledNode target = getNode(n.x + xOffset, n.y + yOffset);
        if (target.type == TileNodeType.NORMAL){
            n.getConnections().add(new FlatTiledConnection(this, n, target));
        }
    }
}
