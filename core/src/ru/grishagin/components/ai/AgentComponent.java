package ru.grishagin.components.ai;

import com.badlogic.ashley.core.Component;
import ru.grishagin.model.ai.AgentState;

public class AgentComponent implements Component {
    private AgentState currentState;

    public AgentComponent() {
        this(AgentState.IDLE);
    }

    public AgentComponent(AgentState currentState) {
        this.currentState = currentState;
    }

    public AgentState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AgentState currentState) {
        this.currentState = currentState;
    }
}
