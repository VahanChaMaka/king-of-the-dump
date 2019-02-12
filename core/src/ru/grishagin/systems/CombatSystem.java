package ru.grishagin.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.grishagin.components.*;
import ru.grishagin.components.items.ArmorComponent;
import ru.grishagin.components.items.WeaponComponent;
import ru.grishagin.components.stats.HealthComponent;
import ru.grishagin.model.actions.TransferAction;
import ru.grishagin.utils.Logger;

public class CombatSystem extends IteratingSystem {

    private ComponentMapper<AttackTargetComponent> atm = ComponentMapper.getFor(AttackTargetComponent.class);
    private ComponentMapper<EquippedWeaponComponent> ewm = ComponentMapper.getFor(EquippedWeaponComponent.class);
    private ComponentMapper<EquippedArmorComponent> eam = ComponentMapper.getFor(EquippedArmorComponent.class);
    private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);
    private ComponentMapper<ArmorComponent> am = ComponentMapper.getFor(ArmorComponent.class);
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public CombatSystem() {
        super(Family.all(PositionComponent.class, HealthComponent.class, EquippedWeaponComponent.class, EquippedArmorComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EquippedWeaponComponent equippedWeaponComponent = ewm.get(entity); //container for weapon entity
        Entity activeWeapon = equippedWeaponComponent.weapon; //a weapon itself
        int attackSpeed = wm.get(activeWeapon).speed;

        //for every entity with weapon update last attack time
        if(equippedWeaponComponent.lastAttack >= attackSpeed){
            equippedWeaponComponent.lastAttack = attackSpeed;
        } else {
            equippedWeaponComponent.lastAttack += deltaTime;
        }

        if(atm.get(entity) != null){
            Entity attackTarget = atm.get(entity).target;
            if(attackTarget != null){
                if(wm.get(activeWeapon).range >= SystemHelper.getDistance(entity, attackTarget)){ //if target in weapon range
                    if(equippedWeaponComponent.lastAttack >= attackSpeed) { //if last attack was long ago, a new one can be performed
                        int damage = calculateAttackResult(entity, attackTarget);
                        if (damage > 0) {
                            HealthComponent targetHealth = hm.get(attackTarget);
                            targetHealth.health = targetHealth.health - damage;
                            if (targetHealth.health <= 0) {//target is dead
                                markDead(attackTarget);
                            }

                            Logger.info(entity.getComponent(NameComponent.class).name + " hits " +
                                    attackTarget.getComponent(NameComponent.class) + " on " + damage +
                                    ". Remained health is " + targetHealth.health);

                        } else if (damage == 0) {

                        } else { //negative damage means miss

                        }

                        equippedWeaponComponent.lastAttack = 0;//mark attack as performed

                        //stop attacking. Player have to click again, NPC should re-add target if it is still requred
                        entity.remove(AttackTargetComponent.class);
                    }
                } else {//if not in range come closer
                    entity.add(new DirectionComponent(attackTarget));
                }
            } else {
                Logger.warning("Attack target is null!");
            }
        }
    }

    private int calculateAttackResult(Entity attacker, Entity defender){
        WeaponComponent weapon = wm.get(ewm.get(attacker).weapon);
        Entity headArmor = eam.get(defender).getHead();
        Entity suitArmor = eam.get(defender).getSuit();

        int appliedDamage = 0;

        if(weapon.damageType == WeaponComponent.DamageType.MELEE){
            int fullMeleeResistance = am.get(suitArmor).meleeResistance + am.get(headArmor).meleeResistance;
            appliedDamage = weapon.damage - fullMeleeResistance;
        } else if(weapon.damageType == WeaponComponent.DamageType.FIREARM){
            int fullFirearmResistance = am.get(suitArmor).firearmResistance + am.get(headArmor).firearmResistance;
            appliedDamage = weapon.damage - fullFirearmResistance;
        }

        //TODO: change weapon and armor condition

        //if resistance higher, then damage
        if(appliedDamage < 0){
            appliedDamage = 0;
        }

        return appliedDamage;
    }

    private void markDead(Entity entity){
        Logger.info(entity.getComponent(NameComponent.class).name + " is dead");

        entity.remove(HealthComponent.class);
        entity.remove(VelocityComponent.class);
        entity.remove(InteractionComponent.class);

        //if there is some items, allow to take a loot
        if(entity.getComponent(InventoryComponent.class) != null) {
            entity.add(new InteractiveComponent(new TransferAction(), 0));
        }
    }

}
