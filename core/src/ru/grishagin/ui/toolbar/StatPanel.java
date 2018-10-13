package ru.grishagin.ui.toolbar;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.GameModel;
import ru.grishagin.ui.UIManager;
import ru.grishagin.ui.Utils.AssetManager;

/**
 * Created by Admin on 14.01.2018.
 */
public class StatPanel extends Table {
    private final static String BACKGROUND = "metal_bottom";

    protected Label healthLabel;
    protected Label hungerLabel;
    protected Label thirstLabel;
    protected Label fatigueLabel;
    protected Label radiationLabel;
    protected Label toxicationLabel;

    public StatPanel() {
        setBackground(new TextureRegionDrawable(AssetManager.getInstance().getTextureRegion(BACKGROUND)));
        pad(10, 16, 20 , 16);
        Table readings = new Table();
        readings.defaults().space(5, 20, 5, 20);

        healthLabel = new Label("Здоровье: " + GameModel.getInstance().getPers().getHealth(),
                AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        hungerLabel = new Label("Голод: " + GameModel.getInstance().getPers().getHunger(),
                AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        thirstLabel = new Label("Жажда: " + GameModel.getInstance().getPers().getThirst(),
                AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        fatigueLabel = new Label("Усталость: " + GameModel.getInstance().getPers().getFatigue(),
                AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        radiationLabel = new Label("Рад. заражение: " + GameModel.getInstance().getPers().getRadiation(),
                AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
        toxicationLabel = new Label("Отравление: " + GameModel.getInstance().getPers().getToxication(),
                AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));

        readings.add(healthLabel).width(180);
        readings.add(hungerLabel).width(135);
        readings.row();
        readings.add(thirstLabel).align(Align.left);
        readings.add(fatigueLabel).align(Align.left);
        readings.row();
        readings.add(radiationLabel).align(Align.left);
        readings.add(toxicationLabel).align(Align.left);

        add(readings).expand().fill();

        debugAll();

        UIManager.getInstance().putPanel(UIManager.STAT_PANEL, this);
    }

    public void updateStat(){
        healthLabel.setText("Здоровье: " + GameModel.getInstance().getPers().getHealth());
        hungerLabel.setText("Голод: " + GameModel.getInstance().getPers().getHunger());
        thirstLabel.setText("Жажда: " + GameModel.getInstance().getPers().getThirst());
        fatigueLabel.setText("Усталость: " + GameModel.getInstance().getPers().getFatigue());
        radiationLabel.setText("Рад. заражение: " + GameModel.getInstance().getPers().getRadiation());
        toxicationLabel.setText("Отравление: " + GameModel.getInstance().getPers().getToxication());
    }

    public void addPersInfoButton(Button button){
        add(button).width(90).height(30).align(Align.bottom);
    }
}
