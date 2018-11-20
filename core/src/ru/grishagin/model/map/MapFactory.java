package ru.grishagin.model.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapFactory {

    public static TiledBasedMap loadMap(){
        return new TiledBasedMap(new TmxMapLoader().load("maps/entrance.tmx"));
    }

    /*public static Map loadMap(){
        int xSize = 32;
        int ySize = 32;
        Map map = new Map(xSize, ySize);

        int id = 0;
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                if(j > 1 && j < 10 && i == 10){
                    if(Math.random() > 0.5) {
                        map.map[i][j] = new MapCell(id++, 34);
                    } else {
                        map.map[i][j] = new MapCell(id++, 38);
                    }
                } else if(i > 1 && i < 10 && j == 2) {
                    if(Math.random() > 0.5) {
                        map.map[i][j] = new MapCell(id++, 33);
                    } else {
                        map.map[i][j] = new MapCell(id++, 37);
                    }
                } else{
                    map.map[i][j] = new MapCell(id++, (int)(Math.random()*32 +1));
                }
            }
        }

        return map;
    }*/
}
