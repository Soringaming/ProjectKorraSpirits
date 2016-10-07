package com.projectkorra.spirits.ability.dark;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.spirits.ability.DarkAbility;
import com.projectkorra.spirits.configuration.ConfigManager;

public class DarkBeam extends DarkAbility {
	
	static long cooldown = ConfigManager.getConfig().getLong("Abilities.Dark.DarkBeam.Cooldown");
	static double range = ConfigManager.getConfig().getDouble("Abilities.Dark.DarkBeam.Range");
	static double damage = ConfigManager.getConfig().getDouble("Abilities.Dark.DarkBeam.Damage");
	
	private double distanceTravelled;
	private Location location;
	private Vector direction;

	public DarkBeam(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, 0.1f);
		
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

			ParticleEffect.LARGE_SMOKE.display((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.1f, 5, location, 257D);
			ParticleEffect.WITCH_MAGIC.display((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.1f, 5, location, 257D);
			
			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1)) {
					DamageHandler.damageEntity(entity, damage, this);
			}
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
		return "DarkBeam";
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
