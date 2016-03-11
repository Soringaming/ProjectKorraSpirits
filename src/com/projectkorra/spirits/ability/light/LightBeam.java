package com.projectkorra.spirits.ability.light;

import com.projectkorra.spirits.ability.LightAbility;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LightBeam extends LightAbility {

	public LightBeam(Player player) {
		super(player);
		start();
	}

	@Override
	public void progress() {
		remove();
		return;
		
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "LightBeam";
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
