package com.projectkorra.spirits.ability.neutral;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.spirits.SpiritPlayer;
import com.projectkorra.spirits.ability.SpiritAbility;

public class SpiritFlight extends SpiritAbility {

	private long flightduration;
	
	private long systemtime;
	private long startTime;
	private boolean isFlying;
	private Location startLoc;
	private Location loc;
	private SpiritPlayer spiritPlayer;
	private double t;
	private double particleHeight;
	private boolean particlesLowered;
	private double startY;
	private boolean loweringPlayer;
	
	public SpiritFlight(Player player) {
		super(player);
		
		if (!bPlayer.canBendIgnoreBinds(this)) {
			return;
		}
		if(check(player)) {
			getAbility(player, SpiritFlight.class).remove();
			return;
		} else {
			player.setVelocity(new Vector(0, 1, 0));
			if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
				player.setAllowFlight(true);
			}
			player.setFlying(true);
			this.isFlying = true;
			setFields();
			start();
		}
		
	}
	
	//Sets fields for setters.
	public void setFields() {
		
		setFlightDuration(10000);
		setSystemTime(System.currentTimeMillis());
		setStartTime(System.currentTimeMillis());
		this.startLoc = player.getLocation();
		this.spiritPlayer = SpiritPlayer.getSpiritPlayer(player);
		this.t = 0;
		this.particlesLowered = true;
		this.particleHeight = 0;
		this.startY = player.getLocation().getY();
		this.loweringPlayer = false;
		
	}
	
	//Checks if the player is using the ability.
	public static boolean check(Player player) {
		
		if (hasAbility(player, SpiritFlight.class)) {
			return true;
		}
		return false;
		
	}
	
	/*
	 * The main progress method.
	 */
	@Override
	public void progress() {
		
		if (player == null || !player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		playSpiritFlightParticls();
		if(startTime + flightduration <= System.currentTimeMillis() || loweringPlayer) {
			lowerPlayer();
			loweringPlayer = true;
			return;
		}
		if(!bPlayer.canBendIgnoreBindsCooldowns(this)) {
			remove();
			return;
		}
		if(player.isSneaking() && bPlayer.getBoundAbility().getName().equalsIgnoreCase("SpiritFlight") && loweringPlayer == false) {
			startY = player.getLocation().getY();
			Jet();
		} else {
			player.setVelocity(player.getVelocity());
			if(player.getLocation().getY() > startY + 4) {
				player.setFlying(false);
			} else if (!player.isFlying()){
				player.setFlying(true);
			}
		}
		
	}
	
	//Jets the player forward.
	public void Jet() {
		
		player.setFallDistance(0);
		Vector dir = player.getLocation().getDirection().clone().multiply(0.6);
		player.setVelocity(dir);
		
	}
	
	public void playSpiritFlightParticls() {
		loc = player.getLocation();
		if(spiritPlayer == null) {
			return;
		}
		t = t + Math.PI/8;
		if (particleHeight <= 2 && particlesLowered == true) {
			particleHeight += 0.5;
			if (particleHeight == 2) {
				particlesLowered = false;
			}
		} else {
			particleHeight -= 0.5;
			if (particleHeight <= 0.5) {
				particlesLowered = true;
			}
		}
		if(spiritPlayer == null) {
			return;
		}
		double r = 1;
		double x = r * Math.sin(t);
		double y = particleHeight;
		double z = r * Math.cos(t);		
		loc.add(x, y, z);
		if(spiritPlayer.isDarkSpirit()) {
			ParticleEffect.WITCH_MAGIC.display(loc, 0.1F, 0.1F, 0.1F, 0.01F, 4);
		}
		if(spiritPlayer.isLightSpirit()) {
			ParticleEffect.FIREWORKS_SPARK.display(loc, 0.1F, 0.1F, 0.1F, 0.01F, 4);
		}
		loc.subtract(x, y, z);

		double x2 = r * Math.cos(t);
		double y2 = particleHeight;
		double z2 = r * Math.sin(t);
		loc.add(x2, y2, z2);
		if(spiritPlayer.isDarkSpirit()) {
			ParticleEffect.WITCH_MAGIC.display(loc, 0.1F, 0.1F, 0.1F, 0.01F, 4);
		}
		if(spiritPlayer.isLightSpirit()) {
			ParticleEffect.FIREWORKS_SPARK.display(loc, 0.1F, 0.1F, 0.11F, 0.01F, 4);
		}
		loc.subtract(x2, y2, z2);
		
	}
	
	//Lowers the player
	@SuppressWarnings("deprecation")
	public void lowerPlayer() {
		player.setFallDistance(0);
		player.setVelocity(new Vector(0, -0.2, 0));
		if(player.isOnGround()) {
			remove();
			return;
		}
	}
	
	//The remove method. Stops the player from flying if they aren't in Creative or Spectator
	@Override
	public void remove() {
		super.remove();
		
		if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR && isFlying) {
			player.setAllowFlight(false);
			player.setFlying(false);
		}
		bPlayer.addCooldown(this);
		
	}

	public long getFlightDuration() {
		return flightduration;
	}

	public void setFlightDuration(long flightduration) {
		this.flightduration = flightduration;
	}

	public long getSystemTime() {
		return systemtime;
	}

	public void setSystemTime(long systemtime) {
		this.systemtime = systemtime;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(long time) {
		this.startTime = time;
	}

	@Override
	public long getCooldown() {
		return 5000;
	}

	@Override
	public Location getLocation() {
		return startLoc;
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
