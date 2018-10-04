package ru.grishagin.model.map;

public class Map {
    private int xSize = 32;
    private  int ySize = 32;
    private MapCell[][] map = new MapCell[ySize][xSize];
    
    public Map(){
        int id = 0;
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                map[i][j] = new MapCell(id++, id%3);
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
