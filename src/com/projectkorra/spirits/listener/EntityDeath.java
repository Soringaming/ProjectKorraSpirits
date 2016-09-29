package com.projectkorra.spirits.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		
		//if (Spirit.isSpirit(event.getEntity())) {
			
			//Spirit spirit = Spirit.getSpirit(event.getEntity());
			//Bukkit.getServer().getPluginManager().callEvent(new SpiritDeathEvent(spirit));
		//}
	}

}
