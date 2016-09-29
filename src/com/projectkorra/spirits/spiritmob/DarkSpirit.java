package com.projectkorra.spirits.spiritmob;

import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

public class DarkSpirit extends Spirit {

	public DarkSpirit() {
		super(EntityType.BAT, "");
		
		addPotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
	}
	
}