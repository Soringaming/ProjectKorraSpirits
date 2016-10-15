package com.projectkorra.spirits.ability.dark;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;
import com.projectkorra.spirits.SpiritPlayer;
import com.projectkorra.spirits.ability.DarkAbility;
import com.projectkorra.spirits.configuration.ConfigManager;

public class Corruption extends DarkAbility {
	
	private long cooldown;
	private double radius;
	private long warmup;
	private long duration;
	private double damage;
	private List<String> convertableTypes;
	private long interval;
	
	private Location origin;
	private boolean isFinished = false;
	private boolean corrupted = false;
	private long time = System.currentTimeMillis();
	private List<TempBlock> tempBlocks = new ArrayList<TempBlock>();
	private List<Block> affectedBlocks = new ArrayList<Block>();

	public Corruption(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		
		setFields();
		convert(player.getLocation());
		bPlayer.addCooldown(this);
		
		start(); 
	}
	
	public void setFields() {
		
		this.cooldown = ConfigManager.getConfig().getLong("Abilities.Dark.Corruption.Cooldown");
		this.radius = ConfigManager.getConfig().getDouble("Abilities.Dark.Corruption.Radius");
		this.warmup = ConfigManager.getConfig().getLong("Abilities.Dark.Corruption.WarmUp");
		this.duration = ConfigManager.getConfig().getLong("Abilities.Dark.Corruption.Duration");
		this.damage = ConfigManager.getConfig().getDouble("Abilities.Dark.Corruption.Damage");
		this.convertableTypes = ConfigManager.getConfig().getStringList("Abilities.Dark.Corruption.ConvertableTypes");
		this.interval = 2000;
	}
	
	public void convert(Location origin) {
		
		this.origin = origin;
		for (Block block : GeneralMethods.getBlocksAroundPoint(origin, radius)) {
			if (convertableTypes.contains(block.getType().toString())) {
				affectedBlocks.add(block);
			}
		}
	}
	
	public void execute() {
		
		for (Block block : affectedBlocks) {
			
			TempBlock tempBlock = new TempBlock(block, Material.MYCEL, (byte) 0);
			tempBlocks.add(tempBlock);
		}
		
		corrupted = true;
	}
	
	public void corrupt(LivingEntity entity) {
		
		if (entity instanceof Player) {
			if (SpiritPlayer.getSpiritPlayer((Player) entity) != null) {
				SpiritPlayer spiritPlayer = SpiritPlayer.getSpiritPlayer((Player) entity);
				if (!spiritPlayer.isDarkSpirit()) {
					applyEffects(entity);
					
					return;
				} else {
					
				}
			}
		}

		applyEffects(entity);
	}
	
	public void applyEffects(LivingEntity entity) {

		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 6000, 1));
		DamageHandler.damageEntity(entity, damage, this);
	}
	
	public void displayParticleAura() {
		
		ParticleEffect.WITCH_MAGIC.display(origin, (float) radius / 2, 2, (float) radius / 2, 0, 10);
		ParticleEffect.SMOKE.display(origin, (float) radius / 2, 2, (float) radius / 2, 0, 10);
	}
	
	public void revertCorruption() {
		
		for (TempBlock tempBlock : tempBlocks) {
			tempBlock.revertBlock();
		}
		
		tempBlocks.clear();
	}

	@Override
	public void progress() {
		
		long currentTime = System.currentTimeMillis();
		
		if (player == null) {
			remove();
			return;
		}
		
		if (isFinished) {
			revertCorruption();
			remove();
			return;
		}
		
		// If the land has been corrupted, then begin to display particles.
		if (corrupted) {

			displayParticleAura();
		}
		
		// If the land has not been corrupted yet, and the warmup has finished, corrupt the land.
		if (!isFinished && !corrupted && currentTime > startTime + warmup) {
			
			execute();
		}
		
		// If the ability has been active for longer than the duration, set finished to true. This will then remove the instance of the ability.
		if (currentTime > startTime + duration) {
			
			isFinished = true;
		}
		
		// With a delay of 'interval', apply corruption effects to all players on the corrupted land.
		if (currentTime - time >= interval && corrupted) {
			
			time = System.currentTimeMillis();
			for (Block block : affectedBlocks) {
				
				Location topBlock = GeneralMethods.getTopBlock(block.getLocation(), 4, 4).getLocation().clone().add(0, 1, 0);
				for (Entity entity : GeneralMethods.getEntitiesAroundPoint(topBlock, 1)) {
					
					if (entity instanceof LivingEntity) {
						if (entity.getEntityId() != player.getEntityId()) {
							corrupt((LivingEntity) entity);
						}
					}
				}
			}
		}
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return origin;
	}

	@Override
	public String getName() {
		return "Corruption";
	}
	
	@Override
	public String getDescription() {
		return "Test description";
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