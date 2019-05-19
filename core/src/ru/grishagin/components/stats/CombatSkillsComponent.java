package ru.grishagin.components.stats;

import com.badlogic.ashley.core.Component;
import ru.grishagin.components.items.WeaponComponent;

import java.util.HashMap;
import java.util.Map;

//Skills from 0 to 100
public class CombatSkillsComponent implements Component {
    private Map<WeaponComponent.DamageType, Integer> skills = new HashMap();

    public CombatSkillsComponent setSkill(WeaponComponent.DamageType type, int skillValue){
        skills.put(type, skillValue);
        return this;
    }

    public int getSkill(WeaponComponent.DamageType type){
        return skills.get(type);
    }
}
