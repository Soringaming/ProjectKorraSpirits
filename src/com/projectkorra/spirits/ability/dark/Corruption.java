package com.projectkorra.spirits.ability.dark;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.projectkorra.spirits.ability.DarkAbility;
import com.projectkorra.spirits.configuration.ConfigManager;

public class Corruption extends DarkAbility {
	
	static long cooldown = ConfigManager.getConfig().getLong("Abilities.Dark.Corruption.Cooldown");
	static double range = ConfigManager.getConfig().getDouble("Abilities.Dark.Corruption.Range");
	
	private double distanceTravelled;
	private Location location;

	public Corruption(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		
		location = player.getEyeLocation();
		bPlayer.addCooldown(this);
		start();
	}

	@Override
	public void progress() {
		
		if (player == null || !player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		
		if (!bPlayer.canBendIgnoreCooldowns(this)) {
			remove();
			return;
		}
		
		if (distanceTravelled >= range) {
			remove();
			return;
		}
		
		player.sendMessage("Activate");
		remove();
		return;
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public String getName() {
		return "Corruption";
	}
	
	@Override
	public String getDescription() {
		return "Test description";
	}

	@Override
	public boolean isExplosiveAbility() {
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isIgniteAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

}