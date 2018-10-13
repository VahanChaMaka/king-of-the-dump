package ru.grishagin.ui.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.Items.Item;
import ru.grishagin.ui.Utils.AssetManager;

/**
 * Created by Admin on 15.08.2017.
 */
public class ItemIcon extends Actor {
    public static final String ITEM_BACKGROUND = "button";
    public static final String ITEM_BACKGROUND_DOWN = "button_1_over";

    private ItemIcon(){}

    public static Actor getItemIcon(Item item){
        //create container anyway. If item=null container should be returned without any image
        Container icon = new Container<Image>();
        icon.background(new TextureRegionDrawable(AssetManager.getInstance().getTextureRegion(ITEM_BACKGROUND)));

        if(item != null) {
            String name = item.getName();
            String[] words = name.split(" ");
            name = "";
            for (String word : words) {
                name = name + word + "\n";
            }

            //change background on press/release
            icon.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    (icon).background(new TextureRegionDrawable(AssetManager.getInstance().getTextureRegion(ITEM_BACKGROUND_DOWN)));
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    (icon).background(new TextureRegionDrawable(AssetManager.getInstance().getTextureRegion(ITEM_BACKGROUND)));
                    super.touchUp(event, x, y, pointer, button);
                }
            });

            //fill container
            if (item.getIconPath() != null) {
                TextureRegion texture = AssetManager.getInstance().getTextureRegion(item.getIconPath());
                icon.setActor(new Image(texture));
                icon.size(50, 50);
            } else {//if item has no image, create simple text button
                Label label = new Label(name, AssetManager.getInstance().getSkin(AssetManager.SIMPLE_SKIN));
                float scale = 55 / label.getWidth();
                if (scale > 1) {
                    scale = 1;
                }
                label.setFontScale(scale);
                label.setAlignment(Align.center);
                icon.setActor(label);
                icon.fill();
            }
        }
        return icon;
    }
}
