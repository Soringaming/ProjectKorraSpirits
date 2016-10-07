package com.projectkorra.spirits.ability.dark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.TempBlock;
import com.projectkorra.spirits.ability.DarkAbility;
import com.projectkorra.spirits.configuration.ConfigManager;

public class Corruption extends DarkAbility {
	
	Material[] convertableTypes = {
			Material.GRASS
	};
	
	public static ConcurrentHashMap<Player, Corruption> instances = new ConcurrentHashMap<Player, Corruption>();
	public static List<Block> corruptedBlocks = new ArrayList<Block>();
	
	static long cooldown = ConfigManager.getConfig().getLong("Abilities.Dark.Corruption.Cooldown");
	static double radius = ConfigManager.getConfig().getDouble("Abilities.Dark.Corruption.Radius");
	static long warmup = ConfigManager.getConfig().getLong("Abilities.Dark.Corruption.WarmUp");
	static long duration = ConfigManager.getConfig().getLong("Abilities.Dark.Corruption.Duration");
	
	private Location origin;
	private boolean isFinished = false;
	private boolean corrupted = false;
	private List<TempBlock> tempBlocks = new ArrayList<TempBlock>();
	private List<Block> affectedBlocks = new ArrayList<Block>();

	public Corruption(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		
		if (instances.containsKey(player)) {
			return;
		}
		
		convert(player.getLocation());
		instances.put(player, this);
		bPlayer.addCooldown(this);
	}
	
	public void convert(Location origin) {
		
		this.origin = origin;
		for (Block block : GeneralMethods.getBlocksAroundPoint(origin, radius)) {
			if (Arrays.asList(convertableTypes).contains(block.getType())) {
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
	
	public void revertCorruption() {
		
		for (TempBlock tempBlock : tempBlocks) {
			tempBlock.revertBlock();
		}
		
		tempBlocks.clear();
	}
	
	public void remove() {
		
		revertCorruption();
		instances.remove(player);
	}

	@Override
	public void progress() {
		
		long currentTime = System.currentTimeMillis();
		
		if (player == null || !player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		
		if (isFinished) {
			remove();
			return;
		}
		
		if (!isFinished && !corrupted && currentTime > startTime + warmup) {
			
			execute();
		}
			
		if (currentTime > startTime + duration) {
			
			isFinished = true;
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