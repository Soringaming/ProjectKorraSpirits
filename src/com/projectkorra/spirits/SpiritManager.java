package com.projectkorra.spirits;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.spirits.spiritmob.DarkSpirit;
import com.projectkorra.spirits.spiritmob.LightSpirit;

public class SpiritManager extends BukkitRunnable {
	
	@Override
	public void run() {
		
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			
			int[] offset = {-1, 0, 1};
			
			World world = player.getWorld();
			int baseX = player.getLocation().getChunk().getX();
			int baseZ = player.getLocation().getChunk().getZ();
			
			for (int x : offset) {
				for (int z : offset) {
					Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
					for (Entity entity : chunk.getEntities()) {
						if (entity instanceof CraftEntity) {
							CraftEntity cEntity = (CraftEntity) entity;
							if (!entity.isDead()) {
								if (cEntity.getHandle() instanceof DarkSpirit) {
					              	ParticleEffect.WITCH_MAGIC.display(entity.getLocation(), 0.25F, 0.25F, 0.25F, 0, 2);
								}
								if (cEntity.getHandle() instanceof LightSpirit) {
					              	ParticleEffect.CLOUD.display(entity.getLocation(), 0.25F, 0.5F, 0.25F, 0, 2);
								}
							}
						}
					}
				}
			}
		}
		
	}
	
}