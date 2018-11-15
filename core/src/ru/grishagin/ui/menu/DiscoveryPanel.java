package ru.grishagin.ui.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Admin on 27.09.2017.
 */

@Deprecated
public class DiscoveryPanel extends Container {
    /*private Cell cell;
    Table layout = new Table();
    private ActionProgressBar progressBar = UIManager.getInstance().getProgressBar();

    public DiscoveryPanel() {
        debugAll();
        this.cell = GameModel.getInstance().getCurrentCell();

        addCellInfo();
        addButtons();

        setActor(layout);
        fill();
    }

    private void addCellInfo(){
        Label name = new Label(cell.getCellType(), AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        layout.add(name).expand().fill();
        layout.row();

        StringBuilder builder = new StringBuilder("");
        builder.append("Уровень радиоактивного заражения: " + cell.getRadiation() + "\n");
        builder.append("Уровень токсического заражения: " + cell.getToxicity() + "\n");
        Label label = new Label(builder.toString(), AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        label.setWrap(true);
        layout.add(label).expand().fill();

        builder = new StringBuilder();
        if(GameModel.getInstance().getCurrentCell().getProbabilitiesNames() != null) {
            builder.append("Здесь можно обнаружить:\n");
            for (String s :
                    GameModel.getInstance().getCurrentCell().getProbabilitiesNames()) {
                builder.append(s + "\n");
            }
            builder.append("");
        }else {
            builder.append("Кажется, что здесь я больше ничего не найду.");
        }
        Label probLabel = new Label(builder.toString(), AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        probLabel.setWrap(true);
        layout.row();
        layout.add(probLabel).expand().fill();
    }

    private void addButtons(){
        if(GameModel.getInstance().getCurrentCell().getProbabilitiesNames() != null) {
            Button button = new TextButton("Искать!", AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    progressBar.run(Timer.SEARCH_TIME);
                    GameModel.getInstance().getCurrentCell().search();
                    update();//may be I should update cell info AFTER hiding progress bar?
                }
            });
            layout.row();
            layout.add(button);
        }
    }


    private void update(){
        layout.clear();
        addCellInfo();
        addButtons();
        ((InventoryMenu)UIManager.getInstance().getPanel(UIManager.CURRENT_INVENTORY_MENU)).updateItemGrid();
    }*/
}
