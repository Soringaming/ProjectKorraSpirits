package com.projectkorra.spirits.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.projectkorra.spirits.events.SpiritAttackEvent;
import com.projectkorra.spirits.events.SpiritDamageEvent;
import com.projectkorra.spirits.spiritmob.Spirit;

public class EntityDamageByEntity implements Listener {
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		
		if (Spirit.isSpirit(entity)) {
			Spirit spirit = Spirit.getSpirit(entity);
			
			Bukkit.getServer().getPluginManager().callEvent(new SpiritDamageEvent(damager, spirit, event.getDamage()));
			
		}
		
		if (Spirit.isSpirit(damager)) {
			Spirit spirit = Spirit.getSpirit(damager);
			
			Bukkit.getServer().getPluginManager().callEvent(new SpiritAttackEvent(spirit, damager, event.getDamage()));
		}
	}
}