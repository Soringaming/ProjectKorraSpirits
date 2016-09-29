package com.projectkorra.spirits.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.projectkorra.spirits.spiritmob.Spirit;

public class SpiritDeathEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Spirit spirit;
	
	/**
	 * Create a new SpiritDeathEvent
	 * @param spirit The spirit killed
	 */
	
	public SpiritDeathEvent(Spirit spirit) {
		this.spirit = spirit;
	}
	
	/**
	 * Gets the spirit killed
	 * @return the spirit killed
	 */
	
	public Spirit getSpirit() {
		return spirit;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
