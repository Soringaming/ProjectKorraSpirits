package com.projectkorra.spirits.spiritmob;

public class Spirit {
	
	/*
	
	public static Map<UUID, Spirit> SPIRITS = new ConcurrentHashMap<UUID, Spirit>();
	public static Map<Spirit, LivingEntity> SPIRIT_ENTITIES = new ConcurrentHashMap<Spirit, LivingEntity>();

	private Spirit spirit;
	private UUID uuid;
	private SpiritType spiritType;
	private EntityType entityType;
	private String displayName;
	private EntityEquipment equipment;
	private Collection<PotionEffect> potionEffects;
	private Collection<ItemStack> drops;
	
	public Spirit(SpiritType spiritType, EntityType entityType, String displayName) {
		
		this.uuid = UUID.randomUUID();
		this.spiritType = spiritType;
		this.entityType = entityType;
		this.displayName = displayName;
		this.equipment = null;
		this.potionEffects = null;
		List<String> dropList = ConfigManager.getConfig().getStringList("Properties.Spirits." + spiritType.toString() + ".Drops");
		List<ItemStack> tempDrops = new ArrayList<ItemStack>();
		for (String string : dropList) {
			String[] splitDrops = string.split(":");
			if (splitDrops[0] != null && splitDrops[1] != null) {
				Material material = Material.getMaterial(splitDrops[0]);
				int amount = Integer.parseInt(splitDrops[1]);
				ItemStack item = new ItemStack(material, amount);
				tempDrops.add(item);
			}
		}
		this.drops = tempDrops;
				
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
	
	public SpiritType getSpiritType() {
		return spiritType;
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
	
	public Collection<ItemStack> getDrops() {
		return drops;
	}
	
	public void setDrops(Collection<ItemStack> drops) {
		this.drops = drops;
	}
	
	public void addDrop(ItemStack drop) {
		if (getDrops() == null) {
			setDrops(new ArrayList<ItemStack>());
		}
		
		getDrops().add(drop);
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
	
	*/

}