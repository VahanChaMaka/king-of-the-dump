package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.DirectionComponent;
import ru.grishagin.components.InteractionComponent;
import ru.grishagin.components.InteractiveComponent;
import ru.grishagin.components.PositionComponent;

public class InteractionSystem extends IteratingSystem {

    private ComponentMapper<InteractiveComponent> interactiveMapper = ComponentMapper.getFor(InteractiveComponent.class);
    private ComponentMapper<InteractionComponent> interactionMapper = ComponentMapper.getFor(InteractionComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public InteractionSystem() {
        super(Family.all(InteractionComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InteractionComponent interactionAim = interactionMapper.get(entity);
        PositionComponent interactionAimPosition = pm.get(interactionAim.aim);
        PositionComponent interactorPosition = pm.get(entity);
        if(Math.abs(interactionAimPosition.x - interactorPosition.x) > 1
                || Math.abs(interactionAimPosition.y - interactorPosition.y) > 1){ //too far from interaction aim, need to go closer
            entity.add(new DirectionComponent(interactionAimPosition.x, interactionAimPosition.y));
        } else {
            interactiveMapper.get(interactionAim.aim).action.execute(entity, interactionAim.aim);
            entity.remove(InteractionComponent.class);
            entity.remove(DirectionComponent.class);
        }
    }
}
