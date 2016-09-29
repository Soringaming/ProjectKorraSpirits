package com.projectkorra.spirits.spiritworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class SpiritWorldGenerator extends ChunkGenerator {
	
	@SuppressWarnings("deprecation")
	public void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
		
		if (x < 256 && x >= 0 && y < 256 && y >= 0 && z < 256 && z >= 0) {
			if (chunk[y >> 4] == null) {
				chunk[y >> 4] = new byte[16 * 16 * 16];
				chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material.getId();
			}
		}
	}
	
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
		
		byte[][] chunk = new byte[world.getMaxHeight() / 16][];
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 2; y++) {
				for (int z = 0; z < 16; z++) {
					setBlock(x, y, z, chunk, Material.QUARTZ);
				}
			}
		}
		
		return chunk;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		
		ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
		return populators;
	}

}