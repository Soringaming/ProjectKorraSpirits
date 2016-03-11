package com.projectkorra.spirits.ability.neutral;

import com.projectkorra.spirits.ability.SpiritAbility;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpiritFlight extends SpiritAbility {

	private long flightduration;
	private double flightspeed;
	
	private long systemtime;
	private boolean isFlying;
	
	public SpiritFlight(Player player) {
		super(player);
		if (!bPlayer.canBend(this) || hasAbility(player, SpiritFlight.class)) {
			return;
		}
		setFields();
		start();
	}
	
	public void setFields() {
		setFlightDuration(1000);
		setFlightSpeed(0.5);
		setFlying(true);
		setSystemTime(System.currentTimeMillis());
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
		player.setFallDistance(0);
 		if (!isFlying()) {
 			player.setVelocity(new Vector(0, 0, 0));
 			return;
 		} else {
 			if (System.currentTimeMillis() > getSystemTime() + getFlightDuration()) {
 				setFlying(false);
 			}
 			Vector velocity = player.getEyeLocation().getDirection().clone().normalize().multiply(getFlightSpeed());
			player.setVelocity(velocity);
 		}
		return;
	}
	
	public static void setFlying(Player player) {
		if (hasAbility(player, SpiritFlight.class)) {
			SpiritFlight sf = (SpiritFlight) getAbility(player, SpiritFlight.class);
			if (!sf.isFlying()) {
				sf.setFlying();
			}
		} else {
			new SpiritFlight(player);
		}
	}
	
	public void setFlying() {
		setSystemTime(System.currentTimeMillis());
		setFlying(true);
	}

	public long getFlightDuration() {
		return flightduration;
	}

	public void setFlightDuration(long flightduration) {
		this.flightduration = flightduration;
	}

	public double getFlightSpeed() {
		return flightspeed;
	}

	public void setFlightSpeed(double flightspeed) {
		this.flightspeed = flightspeed;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}

	public long getSystemTime() {
		return systemtime;
	}

	public void setSystemTime(long systemtime) {
		this.systemtime = systemtime;
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
		return "SpiritFlight";
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
