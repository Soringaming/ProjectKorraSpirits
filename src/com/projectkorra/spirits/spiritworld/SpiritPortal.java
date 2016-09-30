package com.projectkorra.spirits.spiritworld;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.projectkorra.spirits.Generation;

public class SpiritPortal extends Generation {
	
	@SuppressWarnings("deprecation")
	public SpiritPortal(Player player, Location location, byte colour1, byte colour2) {
		super("SpiritPortal", player, location);
		Random randy = new Random();
		
		byte clay1 = 5;
		byte clay2 = 13;
		
		for (int i1 = -2; i1 <= 2; i1++) {
			for (int i2 = -2; i2 <= 2; i2++) {
				byte data = randy.nextInt(100) > 40 ? clay1 : clay2;
				setBlock(location.clone().add(i1, 0, i2), new MaterialData(Material.STAINED_CLAY, data));
			}
		}

		setBlock(location.clone(), Material.BEACON);
		setBlock(location.clone().add(-2, 0, -2), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(-2, 0, 2), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(2, 0, -2), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(2, 0, 2), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(-3, 0, -1), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(-3, 0, 0), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(-3, 0, 1), new MaterialData(Material.WOOD_STEP, (byte) 5));
		
		setBlock(location.clone().add(3, 0, -1), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(3, 0, 0), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(3, 0, 1), new MaterialData(Material.WOOD_STEP, (byte) 5));
		
		setBlock(location.clone().add(-1, 0, 3), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(0, 0, 3), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(1, 0, 3), new MaterialData(Material.WOOD_STEP, (byte) 5));
		
		setBlock(location.clone().add(-1, 0, -3), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(0, 0, -3), new MaterialData(Material.WOOD_STEP, (byte) 5));
		setBlock(location.clone().add(1, 0, -3), new MaterialData(Material.WOOD_STEP, (byte) 5));
		
		for (int i1 = -1; i1 <= 1; i1++) {
			for (int i2 = -1; i2 <= 1; i2++) {
				for (int i3 = -1; i3 > -6; i3--) {
					if (i1 == 0 && i2 == 0) {
						setBlock(location.clone().add(0, i3, 0), Material.AIR); 
						continue;
					}
					byte data = randy.nextInt(100) > 40 ? clay1 : clay2;
					setBlock(location.clone().add(i1, i3, i2), new MaterialData(Material.STAINED_CLAY, data));
				}
			}
		}

		for (int i1 = -1; i1 <= 1; i1++) {
			for (int i2 = -1; i2 <= 1; i2++) {
				setBlock(location.clone().add(i1, -6, i2), Material.IRON_BLOCK);
			}
		}
		setBlock(location.clone().add(0, -5, 0), Material.BEACON);
		setBlock(location.clone().add(0, -4, 0), new MaterialData(Material.STAINED_GLASS, (byte) colour1));
		
		int m = (location.getWorld().getMaxHeight() - location.getBlockY()) / 3 - 3;
		
		for (int counter1 = 0; counter1 < m; counter1++) {
			setBlock(location.clone().add(-2, 1 + counter1 * 3, -1), new MaterialData(Material.STAINED_GLASS_PANE, colour1));
			setBlock(location.clone().add(-2, 2 + counter1 * 3, 0), new MaterialData(Material.STAINED_GLASS_PANE, colour1));
			setBlock(location.clone().add(-2, 3 + counter1 * 3, 1), new MaterialData(Material.STAINED_GLASS_PANE, colour1));
			
			setBlock(location.clone().add(2, 1 + counter1 * 3, 1), new MaterialData(Material.STAINED_GLASS_PANE, colour1));
			setBlock(location.clone().add(2, 2 + counter1 * 3, 0), new MaterialData(Material.STAINED_GLASS_PANE, colour1));
			setBlock(location.clone().add(2, 3 + counter1 * 3, -1), new MaterialData(Material.STAINED_GLASS_PANE, colour1));
			
			setBlock(location.clone().add(1, 1 + counter1 * 3, -2), new MaterialData(Material.STAINED_GLASS_PANE, colour2));
			setBlock(location.clone().add(0, 2 + counter1 * 3, -2), new MaterialData(Material.STAINED_GLASS_PANE, colour2));
			setBlock(location.clone().add(-1, 3 + counter1 * 3, -2), new MaterialData(Material.STAINED_GLASS_PANE, colour2));
			
			setBlock(location.clone().add(-1, 1 + counter1 * 3, 2), new MaterialData(Material.STAINED_GLASS_PANE, colour2));
			setBlock(location.clone().add(0, 2 + counter1 * 3, 2), new MaterialData(Material.STAINED_GLASS_PANE, colour2));
			setBlock(location.clone().add(1, 3 + counter1 * 3, 2), new MaterialData(Material.STAINED_GLASS_PANE, colour2));
			
			//Swap colours
			byte colourOld = colour1;
			colour1 = colour2;
			colour2 = colourOld;
			
		}
	}

}
