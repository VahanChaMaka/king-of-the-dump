package ru.grishagin.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Admin on 18.09.2017.
 */
public class CentralPanel extends Container {
    //This container is stretched on the full screen to apply tinting effect on the map
    //The inner container contains the menu
    private Container innerContainer;

    public CentralPanel(){
        innerContainer = new Container();
        super.setActor(innerContainer);

        Pixmap tinting = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        tinting.setColor(new Color(0, 0, 0, 0.8f));
        tinting.fill();
        setBackground(new Image(new Texture(tinting)).getDrawable());

        setVisible(false);
    }

    @Override
    public void setActor(Actor actor) {
        innerContainer.setActor(actor);
        innerContainer.width(800);
        innerContainer.height(400);
    }


}
