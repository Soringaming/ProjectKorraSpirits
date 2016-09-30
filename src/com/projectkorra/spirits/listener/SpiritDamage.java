package com.projectkorra.spirits.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.projectkorra.spirits.events.SpiritDamageEvent;

public class SpiritDamage implements Listener {
	
	@EventHandler
	public void onSpiritDamage(SpiritDamageEvent event) {
		
		/*
		if (event.getSpirit() instanceof DarkSpirit) {
			DarkSpirit darkSpirit = (DarkSpirit) event.getSpirit();
			LivingEntity dsEntity = darkSpirit.getEntity();
			
			dsEntity.getWorld().playSound(dsEntity.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 1, 2F);
			ParticleEffect.INSTANT_SPELL.display(dsEntity.getLocation(), 0.1F, 0.1F, 0.1F, 0.01F, 25);
		}
		*/
	}

}