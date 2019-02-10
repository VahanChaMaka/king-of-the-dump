package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.*;
import ru.grishagin.components.items.WeaponComponent;
import ru.grishagin.components.stats.HealthComponent;
import ru.grishagin.utils.Logger;

public class CombatSystem extends IteratingSystem {

    private ComponentMapper<AttackTargetComponent> atm = ComponentMapper.getFor(AttackTargetComponent.class);
    private ComponentMapper<ArmedWeaponComponent> awm = ComponentMapper.getFor(ArmedWeaponComponent.class);
    private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);

    public CombatSystem() {
        super(Family.all(PositionComponent.class, HealthComponent.class, ArmedWeaponComponent.class, ArmedArmorComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(atm.get(entity) != null){
            Entity attackTarget = atm.get(entity).target;
            if(attackTarget != null){
                Entity activeWeapon = awm.get(entity).activeWeapon;
                if(wm.get(activeWeapon).range <= SystemHelper.getDistance(entity, attackTarget)){ //if target in weapon range

                } else {//if not in range come closer
                    entity.add(new DirectionComponent(attackTarget));
                }
            } else {
                Logger.log("Warning! Attack target is null!");
            }
        }
    }


}
