package com.projectkorra.spirits.spiritmob;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

import net.minecraft.server.v1_10_R1.Entity;

public enum SpiritType {
	
	/*
	 * Mob ID list
	 * 
	 * http://minecraft-ids.grahamedgecombe.com/entities
	 */

	DARK_SPIRIT("DarkSpirit", 52, DarkSpirit.class),
	LIGHT_SPIRIT("LightSpirit", 91, LightSpirit.class);

	private String name;
	
	SpiritType(String name, int id, Class<? extends Entity> custom) {
		addToMaps(custom, name, id);
		this.name = name;
	}
	
	public static void spawnEntity(Entity entity, Location location) {
		entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftWorld) location.getWorld()).getHandle().addEntity(entity);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void addToMaps(Class clazz, String name, int id) {
		
		((Map) SpiritMobUtils.getPrivateField("c", net.minecraft.server.v1_10_R1.EntityTypes.class, null)).put(name, clazz);
		((Map) SpiritMobUtils.getPrivateField("d", net.minecraft.server.v1_10_R1.EntityTypes.class, null)).put(clazz, name);
		((Map) SpiritMobUtils.getPrivateField("f", net.minecraft.server.v1_10_R1.EntityTypes.class, null)).put(clazz,  Integer.valueOf(id));
	}
	
	public String getSpiritName() {
		return name;
	}
}
