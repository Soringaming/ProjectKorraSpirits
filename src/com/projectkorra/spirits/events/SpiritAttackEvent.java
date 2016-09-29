package com.projectkorra.spirits.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.projectkorra.spirits.spiritmob.Spirit;

public class SpiritAttackEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled = false;
	private Spirit spirit;
	private Entity entity;
	private double damage;
	
	/**
	 * Create a new SpiritAttackEvent
	 * @param spirit The spirit which attacked
	 * @param entity The entity it damaged
	 * @param damage The amount of damage dealt
	 */
	
	public SpiritAttackEvent(Spirit spirit, Entity entity, double damage) {
		this.spirit = spirit;
		this.entity = entity;
		this.damage = damage;
	}
	
	/**
	 * Gets the attacking spirit
	 * @return the attacking spirit
	 */
	
	public Spirit getSpirit() {
		return spirit;
	}
	
	/**
	 * Gets the entity that was damaged
	 * @return the damaged entity
	 */
	
	public Entity getEntity() {
		return entity;
	}
	
	/**
	 * Returns the damage dealt by the spirit.
	 * @return the amount of damage done
	 */
	
	public double getDamage() {
		return damage;
	}
	
	/**
	 * Sets the damage dealt by the spirit
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