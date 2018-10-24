package ru.grishagin.ui.toolbar;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.components.stats.*;
import ru.grishagin.model.GameModel;
import ru.grishagin.utils.AssetManager;
import ru.grishagin.utils.UIManager;

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
        setBackground(new TextureRegionDrawable(AssetManager.instance.getUITexture(BACKGROUND)));
        pad(10, 16, 20 , 16);
        Table readings = new Table();
        readings.defaults().space(5, 20, 5, 20);

        Entity player = GameModel.instance.getPlayer();

        healthLabel = new Label("", AssetManager.instance.getDefaultSkin());
        hungerLabel = new Label("", AssetManager.instance.getDefaultSkin());
        thirstLabel = new Label("", AssetManager.instance.getDefaultSkin());
        fatigueLabel = new Label("", AssetManager.instance.getDefaultSkin());
        radiationLabel = new Label("", AssetManager.instance.getDefaultSkin());
        toxicationLabel = new Label("", AssetManager.instance.getDefaultSkin());
        updateStat();

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

        UIManager.instance.putPanel(UIManager.STAT_PANEL, this);
    }

    public void updateStat(){
        Entity player = GameModel.instance.getPlayer();

        healthLabel.setText("Здоровье: " + player.getComponent(HealthComponent.class).health);
        hungerLabel.setText("Голод: " + player.getComponent(HungerComponent.class).hunger);
        thirstLabel.setText("Жажда: " + player.getComponent(ThirstComponent.class).thirst);
        fatigueLabel.setText("Усталость: " + player.getComponent(FatigueComponent.class).fatigue);
        radiationLabel.setText("Рад. заражение: " + player.getComponent(RadDoseComponent.class).dose);
        toxicationLabel.setText("Отравление: " + player.getComponent(ToxicityDoseComponent.class).dose);
    }

    public void addPersInfoButton(Button button){
        add(button).width(90).height(30).align(Align.bottom);
    }
}
