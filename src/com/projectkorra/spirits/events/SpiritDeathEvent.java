package com.projectkorra.spirits.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.projectkorra.spirits.spiritmob.SpiritType;

public class SpiritDeathEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private SpiritType spirit;
	
	/**
	 * Create a new SpiritDeathEvent
	 * @param spirit The spirit killed
	 */
	
	public SpiritDeathEvent(SpiritType spirit) {
		this.spirit = spirit;
	}
	
	/**
	 * Gets the spirit killed
	 * @return the spirit killed
	 */
	
	public SpiritType getSpirit() {
		return spirit;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
