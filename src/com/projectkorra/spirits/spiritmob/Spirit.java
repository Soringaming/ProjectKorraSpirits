package com.projectkorra.spirits.spiritmob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.spirits.events.SpiritSpawnEvent;

public class Spirit {
	
	public static Map<UUID, Spirit> SPIRITS = new ConcurrentHashMap<UUID, Spirit>();
	public static Map<Spirit, LivingEntity> SPIRIT_ENTITIES = new ConcurrentHashMap<Spirit, LivingEntity>();

	private Spirit spirit;
	private UUID uuid;
	private EntityType entityType;
	private String displayName;
	private EntityEquipment equipment;
	private Collection<PotionEffect> potionEffects;
	
	public Spirit(EntityType entityType, String displayName) {
		
		this.uuid = UUID.randomUUID();
		this.entityType = entityType;
		this.displayName = displayName;
		this.equipment = null;
		this.potionEffects = null;
		this.spirit = this;
	}
	
	public Spirit getSpirit() {
		return spirit;
	}
	
	public UUID getUniqueId() {
		return uuid;
	}
	
	public void setUniqueId(UUID uuid) {
		this.uuid = uuid;
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
	
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public EntityEquipment getEquipment() {
		return equipment;
	}
	
	public void setEquipment(EntityEquipment equipment) {
		this.equipment = equipment;
	}
	
	public Collection<PotionEffect> getPotionEffects() {
		return potionEffects;
	}
	
	public void setPotionEffects(Collection<PotionEffect> potionEffects) {
		this.potionEffects = potionEffects;
	}
	
	public void addPotionEffect(PotionEffectType type, int duration, int amplifier) {
		if (getPotionEffects() == null) {
			setPotionEffects(new ArrayList<PotionEffect>());
		}
		
		getPotionEffects().add(new PotionEffect(type, duration, amplifier));
	}
	
	public LivingEntity getEntity() {
		
		return SPIRIT_ENTITIES.get(this);
	}
	
	public void spawn(Location spawnLocation) {
		
		Entity entity = spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);
		if (entity instanceof LivingEntity) {
			LivingEntity spirit = (LivingEntity) entity;
			getSpirit().setUniqueId(spirit.getUniqueId());
			spirit.setCustomName(getDisplayName());
			
			// TODO add equipment - cbf rn
			
			if (getPotionEffects() != null) {
				for (PotionEffect effect : getPotionEffects()) {
					spirit.addPotionEffect(effect);
				}
			}
			
			SPIRITS.put(uuid, this);
			SPIRIT_ENTITIES.put(this, spirit);

			Bukkit.getServer().getPluginManager().callEvent(new SpiritSpawnEvent(getSpirit(spirit)));
		} else {
			System.out.println("[ProjectKorraSpirits] ERROR: " + entityType + " is not a valid Spirit mob type.");
			entity.remove();
		}
	}
	
	public static boolean isSpirit(Entity entity) {
		
		return SPIRITS.containsKey(entity.getUniqueId());
	}
	
	public static Spirit getSpirit(Entity entity) {
		
		return isSpirit(entity) ? SPIRITS.get(entity.getUniqueId()) : null;
	}
	
	public static Collection<Spirit> getSpirits() {
		
		return SPIRITS.values();
	}

}