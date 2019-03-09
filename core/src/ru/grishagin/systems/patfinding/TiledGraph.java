package ru.grishagin.systems.patfinding;

import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import ru.grishagin.model.map.TiledBasedMap;

public interface TiledGraph <N extends TiledNode<N>> extends IndexedGraph<N> {

    //public void init (int roomCount, int roomMinSize, int roomMaxSize, int squashIterations);

    public void init(TiledBasedMap map);

    public N getNode (int x, int y);

    public N getNode (int index);

    public void changeNodeType(int index, TileNodeType type);
}
