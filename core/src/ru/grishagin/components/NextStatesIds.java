package ru.grishagin.components;

import com.badlogic.ashley.core.Component;

import java.util.HashMap;
import java.util.Map;

public class NextStatesIds implements Component {
    public final Map<String, Integer> states;

    public NextStatesIds(Map<String, Integer> states) {
        this.states = states;
    }

    public NextStatesIds() {
        this.states = new HashMap<>();
    }

    public void put(String stateName, Integer id){
        states.put(stateName, id);
    }
}
