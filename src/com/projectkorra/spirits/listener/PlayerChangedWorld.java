package com.projectkorra.spirits.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.projectkorra.spirits.SpiritMethods;
import com.projectkorra.spirits.configuration.ConfigManager;

public class PlayerChangedWorld implements Listener {
	
	@EventHandler
	public void onPlayedChangeWorld(PlayerChangedWorldEvent event) {
		
		if (event.getFrom().getName().equals(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName"))) {
			
			SpiritMethods.displayBar(event.getPlayer(), false);
			// LEFT THE SPIRIT WORLD
			
		}
		
		if (event.getPlayer().getWorld().getName().equals(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName"))) {

			SpiritMethods.displayBar(event.getPlayer(), true);
			// ENTERED SPIRIT WORLD
			
		}
	}

}
