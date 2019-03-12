package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.DestinationComponent;
import ru.grishagin.components.InteractionComponent;
import ru.grishagin.components.InteractiveComponent;
import ru.grishagin.components.PositionComponent;

public class InteractionSystem extends IteratingSystem {
    private static final float RANGE = 1.1f;

    private ComponentMapper<InteractiveComponent> interactiveMapper = ComponentMapper.getFor(InteractiveComponent.class);
    private ComponentMapper<InteractionComponent> interactionMapper = ComponentMapper.getFor(InteractionComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<DestinationComponent> dm = ComponentMapper.getFor(DestinationComponent.class);

    public InteractionSystem() {
        super(Family.all(InteractionComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InteractionComponent interactionAim = interactionMapper.get(entity);
        PositionComponent interactionAimPosition = pm.get(interactionAim.aim);
        PositionComponent interactorPosition = pm.get(entity);
        DestinationComponent interactorDestination = dm.get(entity);

        //check if we are close enough
        float distance = SystemHelper.getDistance(entity, interactionAim.aim);
        if(distance > RANGE){ //too far from interaction aim, need to go closer
            //add new destination if it doesn't exist
            if(interactorDestination == null){
                entity.add(new DestinationComponent(interactionAimPosition.x, interactionAimPosition.y));
            }
        } else {
            interactiveMapper.get(interactionAim.aim).action.execute(entity, interactionAim.aim);
            entity.remove(InteractionComponent.class);
            entity.remove(DestinationComponent.class);
        }
    }
}
