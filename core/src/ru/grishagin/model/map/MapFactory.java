package ru.grishagin.model.map;

public class MapFactory {

    public static Map loadMap(){
        int xSize = 32;
        int ySize = 32;
        Map map = new Map(xSize, ySize);

        int id = 0;
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                if(j > 1 && j < 10 && i == 10){
                    map.map[i][j] = new MapCell(id++, 34);
                } else {
                    map.map[i][j] = new MapCell(id++, (int)(Math.random()*32 +1));
                }
            }
        }

        return map;
    }
}
