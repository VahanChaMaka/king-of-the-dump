package ru.grishagin.ui.toolbar;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.grishagin.utils.AssetManager;

import java.util.Calendar;

import static ru.grishagin.ui.toolbar.BottomToolbar.BTN_GROUP_BACKGROUND;

/**
 * Created by Admin on 23.01.2018.
 */
public class Console extends Container{

    public static final String CONSOLE_BACKGROUND = "ui/screen.png";

    private Calendar calendar;

    private Label time;
    private Label date;

    private VerticalGroup messageTray;
    ScrollPane scrollPane;
    private Table innerLayout = new Table();

    public Console(Calendar calendar){
        this.calendar = calendar;

        time = new Label(getTime(), AssetManager.instance.getDefaultSkin());
        date = new Label(getDate(), AssetManager.instance.getDefaultSkin());

        innerLayout.add(time);
        innerLayout.add(date);
        innerLayout.row();

        messageTray = new VerticalGroup();
        messageTray.fill().expand().align(Align.left).align(Align.top);
        scrollPane = new ScrollPane(messageTray);
        innerLayout.add(scrollPane).fill().expand().colspan(2);

        innerLayout.padTop(5).padBottom(5).padLeft(15).padRight(15);
        innerLayout.background(new TextureRegionDrawable(AssetManager.instance.getUITexture(CONSOLE_BACKGROUND)));
        background(new TextureRegionDrawable(AssetManager.instance.getUITexture(BTN_GROUP_BACKGROUND)));

        setActor(innerLayout);
        pad(7);
        fill();
    }

    public void update (){
        time.setText(getTime());
        date.setText(getDate());
    }

    private String getTime(){
        StringBuilder builder = new StringBuilder();
        builder.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":");
        if(calendar.get(Calendar.MINUTE) < 10){
            builder.append("0");
        }
        builder.append(calendar.get(Calendar.MINUTE)).append(":");
        if(calendar.get(Calendar.SECOND) < 10){
            builder.append("0");
        }
        builder.append(calendar.get(Calendar.SECOND));
        return builder.toString();
    }

    private String getDate(){
        StringBuilder builder = new StringBuilder();
        if(calendar.get(Calendar.DATE) < 10){
            builder.append("0");
        }
        builder.append(calendar.get(Calendar.DATE)).append(".");
        if(calendar.get(Calendar.MONTH) < 10){
            builder.append("0");
        }
        builder.append(calendar.get(Calendar.MONTH)).append(".").append(calendar.get(Calendar.YEAR));
        return builder.toString();
    }

    public void printMessage(String message){
        messageTray.addActor(new Label(message, AssetManager.instance.getDefaultSkin()));
        scrollPane.layout();
        scrollPane.scrollTo(0, 0, 0, 0);
    }
}
