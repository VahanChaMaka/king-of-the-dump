package ru.grishagin.utils;

import com.badlogic.ashley.core.Entity;
import ru.grishagin.components.NameComponent;

public class Logger {

    //public static final Logger instance = new Logger();

    private Logger(){
    }

    public static void error(Exception e) {
        e.printStackTrace();
    }

    public static void warning(String message){
        System.out.println("Warning! " + message);
    }

    public static void warning(Entity entity, String message){
        if(entity.getComponent(NameComponent.class) != null){
            warning(entity.getComponent(NameComponent.class).name + ": " + message);
        } else {
            warning(message);
        }
    }

    public static void info(String message){
        System.out.println(message);
    }

    public static void info(Entity entity, String message){
        if(entity.getComponent(NameComponent.class) != null){
            info(entity.getComponent(NameComponent.class).name + ": ");
        } else {
            info(message);
        }
    }
}
