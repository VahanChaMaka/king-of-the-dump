package ru.grishagin.utils;

public class Logger {

    //public static final Logger instance = new Logger();

    private Logger(){
    }

    public static void log(String message){
        System.out.println(message);
    }
}
