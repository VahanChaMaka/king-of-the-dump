package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

import java.util.HashMap;
import java.util.Map;

//All possible sounds an entity can produce
public class SoundComponent implements Component {
    //EventType as string from ru.grishagin.model.messages.MessageType to sound file name
    private final Map<String, String> sounds;

    public SoundComponent(Map<String, String> sounds) {
        this.sounds = sounds;
    }

    public String get(int eventType){
        return sounds.get(String.valueOf(eventType));
    }
}
