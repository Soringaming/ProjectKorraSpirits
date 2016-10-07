package com.projectkorra.spirits.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.projectkorra.spirits.spiritmob.SpiritType;

public class SpiritSpawnEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled = false;
	private SpiritType spirit;
	
	/**
	 * Create a new SpiritSpawnEvent
	 * @param spirit The spirit spawned
	 */
	
	public SpiritSpawnEvent(SpiritType spirit) {
		this.spirit = spirit;
	}
	
	/**
	 * Gets the spirit spawned
	 * @return the spirit spawned
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

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
		
	}

}
