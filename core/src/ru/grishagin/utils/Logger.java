package ru.grishagin.utils;

public class Logger {

    //public static final Logger instance = new Logger();

    private Logger(){
    }

    public static void warning(String message){
        System.out.println("Warning! " + message);
    }

    public static void info(String message){
        System.out.println(message);
    }
}
