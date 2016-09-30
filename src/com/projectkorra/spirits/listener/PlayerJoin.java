package com.projectkorra.spirits.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.projectkorra.spirits.SpiritMethods;
import com.projectkorra.spirits.configuration.ConfigManager;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		if (event.getPlayer().getWorld().getName().equals(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName"))) {
			SpiritMethods.displayBar(event.getPlayer(), true);
		}
	}

}
