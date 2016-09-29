package com.projectkorra.spirits.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.projectkorra.spirits.spiritmob.Spirit;

public class SpiritDamageEvent extends Event implements Cancellable {
	
private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled = false;
	private Entity damager;
	private Spirit spirit;
	private double damage;
	
	/**
	 * Create a new SpiritDamageEvent
	 * @param damager The damager
	 * @param spirit The spirit which is attacked
	 * @param damage The amount of damage dealt
	 */
	
	public SpiritDamageEvent(Entity damager, Spirit spirit, double damage) {
		this.damager = damager;
		this.spirit = spirit;
		this.damage = damage;
	}
	
	/**
	 * Gets the damager
	 * @return the damager
	 */
	
	public Entity getDamager() {
		return damager;
	}
	
	/**
	 * Gets the spirit
	 * @return the spirit
	 */
	
	public Spirit getSpirit() {
		return spirit;
	}
	
	/**
	 * Returns the damage dealt by the damager.
	 * @return the amount of damage done
	 */
	
	public double getDamage() {
		return damage;
	}
	
	/**
	 * Sets the damage dealt by the damager
	 * @param damage the amount of damage done
	 */
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
		
	}

}
