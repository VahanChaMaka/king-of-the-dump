package ru.grishagin.view;

@Deprecated
public class RenderingEngine{
    /*ComponentMapper<SpriteComponent> tm = ComponentMapper.getFor(SpriteComponent.class);
    ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private SpriteBatch batch;
    private Map map;
    private Engine engine;
    private Camera camera;

    private boolean isoMode = true;

    public RenderingEngine(SpriteBatch batch, Map map, Engine engine) {
        super(map, batch);
        this.batch = batch;
        this.map = map;
        this.engine = engine;
    }

    @Override
    public void draw(OrthographicCamera camera){
        this.camera = camera;

        for (int i = 0; i < map.getxSize() ; i++) {
            for (int j = map.getySize() - 1; j >= 0; j--) {
                //draw(AssetManager.instance.getTileTexture(map.getCell(i, j).getTypeId()), i, j, 0, 0);
            }
        }

        for (int i = 0; i < map.getxSize() ; i++) {
            for (int j = map.getySize() - 1; j >= 0; j--) {
                draw(AssetManager.instance.getTileTexture(map.getCell(i, j).getTypeId()), i, j, 0, 0);
                drawCellObjects(i, j);
            }
        }
    }

    //sort and return all drawable objects is cell
    private void drawCellObjects(int x, int y){

        ImmutableArray<Entity> allDrawables = engine.getEntitiesFor(Family.all(SpriteComponent.class, PositionComponent.class).get());
        for (int i = 0; i < allDrawables.size(); i++) {
            Entity entity = allDrawables.get(i);
            float objectX = pm.get(entity).x;
            float objectY = pm.get(entity).y;
            float offsetX = tm.get(entity).offset.x;
            float offsetY = tm.get(entity).offset.y;
            if((int)objectX == x && (int)objectY == y){
                draw(tm.get(entity).sprite, objectX, objectY, offsetX, offsetY);
            }
        }
    }

    private void draw(TextureRegion texture, float x, float y, float offsetX, float offsetY){
        Vector3 renderPosition = new Vector3();
        if(isoMode) {//Isometric view
            int posY = -(int) (x * 32);
            int posX = (int) (y * 32);
            renderPosition.x = (posX - posY);
            renderPosition.y = (posX + posY) / 2;
        } else {//non-isometric view
            renderPosition.x = x;
            renderPosition.y = y;
        }

        //update render position from camera
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(texture, renderPosition.x + offsetX, renderPosition.y+offsetY);
        batch.end();
    }*/
}
