package com.projectkorra.spirits.ability.dark;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.spirits.ability.DarkAbility;

public class DarkBeam extends DarkAbility {

	private double range;
	private double distanceTravelled;
	private Location location;
	private Vector direction;

	public DarkBeam(Player player) {
		super(player);
		range = 40;
		location = player.getEyeLocation();
		direction = player.getEyeLocation().getDirection().normalize();
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
		progressBeam();
		return;
	}

	private void progressBeam() {
		for (int i = 0; i < 2; i++) {
			distanceTravelled++;
			if (distanceTravelled >= range) {
				return;
			}

			location = location.add(direction.clone().multiply(1));
			if (GeneralMethods.isSolid(location.getBlock()) || ElementalAbility.isWater(location.getBlock())) {
				distanceTravelled = range;
				return;
			}

			ParticleEffect.LARGE_SMOKE.display((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.1f, 5, location, 257D);
			ParticleEffect.WITCH_MAGIC.display((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.1f, 5, location, 257D);
		}
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
		return "DarkBeam";
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
