package com.projectkorra.spirits.ability.light;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.spirits.ability.LightAbility;
import com.projectkorra.spirits.configuration.ConfigManager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LightBeam extends LightAbility {

	private long cooldown;
	private double range;
	private double distanceTravelled;
	private Location location;
	private Vector direction;

	public LightBeam(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		
		cooldown = ConfigManager.getConfig().getLong("Abilities.Light.LightBeam.Cooldown");
		range = ConfigManager.getConfig().getDouble("Abilities.Light.LightBeam.Range");
		location = player.getEyeLocation();
		direction = player.getEyeLocation().getDirection().normalize();
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
		
		progressBeam();
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

			ParticleEffect.CLOUD.display((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.1f, 5, location, 257D);
			/*for (int p = 0; p < 5; p++) {
				GeneralMethods.displayColoredParticle(location, "ffd700", (float) Math.random(), (float) Math.random(), (float)Math.random());
			}*/
		}
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
		return "LightBeam";
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
