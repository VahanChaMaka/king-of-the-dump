package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.AttackTargetComponent;
import ru.grishagin.components.PositionComponent;
import ru.grishagin.components.ai.AgentComponent;
import ru.grishagin.components.tags.HostileTag;
import ru.grishagin.model.GameModel;
import ru.grishagin.model.ai.AgentState;

public class AISystem extends IteratingSystem {
    private static final int SIGHT_RADIUS = 3;

    private ComponentMapper<AgentComponent> am = ComponentMapper.getFor(AgentComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<AttackTargetComponent> atm = ComponentMapper.getFor(AttackTargetComponent.class);

    public AISystem() {
        super(Family.all(AgentComponent.class, HostileTag.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //combating only with player by now
        Entity player = GameModel.instance.getPlayer();

        AgentComponent agent = am.get(entity);
        switch (agent.getCurrentState()) {
            case IDLE:
                if(isEnemyNear(entity, player)){
                    agent.setCurrentState(AgentState.ATTACK);
                }
                break;
            case ATTACK:
                AttackTargetComponent attackTargetComponent = atm.get(entity);
                if(attackTargetComponent == null){
                    entity.add(new AttackTargetComponent(player));
                }

        }
    }

    private boolean isEnemyNear(Entity entity, Entity enemy){
        //TODO: check obstacles
        return SystemHelper.getDistance(entity, enemy) < SIGHT_RADIUS;
    }
}
