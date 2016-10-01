package com.projectkorra.spirits.ability.light;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.projectkorra.spirits.ability.LightAbility;
import com.projectkorra.spirits.configuration.ConfigManager;

public class Contain extends LightAbility {
	
	private long cooldown;
	private double range;
	private double distanceTravelled;
	private Location location;

	public Contain(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		
		cooldown = ConfigManager.getConfig().getLong("Abilities.Light.Contain.Cooldown");
		range = ConfigManager.getConfig().getDouble("Abilities.Light.Contain.Range");
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
		
		// TODO stuff
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
		return "Contain";
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
