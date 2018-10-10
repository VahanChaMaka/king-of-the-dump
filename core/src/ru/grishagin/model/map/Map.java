package ru.grishagin.model.map;

public class Map {
    private int xSize = 32;
    private  int ySize = 32;
    MapCell[][] map;

    public Map(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;

         map = new MapCell[ySize][xSize];
    }

    private Map(){
        map = new MapCell[ySize][xSize];

        int id = 0;
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                if(j > 1 && j < 10 && i == 10){
                    map[i][j] = new MapCell(id++, 34);
                } else {
                    map[i][j] = new MapCell(id++, (int)(Math.random()*32 +1));
                }
            }
        }
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public MapCell getCell(int x, int y){
        if(x >= 0 && x < xSize && y >=0  && y < ySize){
            //return map[x][y]; //possible impact on local maps action
            return map[y][x];
        } else {
            return null;
        }
    }
}
