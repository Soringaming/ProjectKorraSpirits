package com.projectkorra.spirits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class Generation {
	
	/*
	 * 
	 * Generation System by StrangeOne101.
	 * 
	 */
	
	public static List<OldGeneration> gens = new ArrayList<OldGeneration>();
	
	public Map<Location, MaterialData> genchanges = new HashMap<Location, MaterialData>();
	public String title = "UNKNOWN_GENERATION";
	public Player creator;
	public Location origins;
	
	public Generation(String title, Player player, Location location) 
	{
		this.title = title;
		this.creator = player;
		this.origins = location;
	}
	
	public void setBlock(Location loc, MaterialData data) 
	{
		this.genchanges.put(loc, data);
	}
	
	public void setBlock(Location loc, Material material) 
	{
		this.genchanges.put(loc, new MaterialData(material));
	}
	
	public void setBlock(World world, int x, int y, int z, MaterialData data)
	{
		setBlock(new Location(world, x, y, z), data);
	}
	
	/**<i><b>Julie! Do the thing!</b></i> Generates the object in the world.*/
	public void generate()
	{
		this.creator.sendMessage(ChatColor.GRAY + "[" + genchanges.size() + " blocks created]");
		
		List<BlockChange> changes = new ArrayList<BlockChange>();
		
		int i = 0;
		for (Location loc : this.genchanges.keySet()) {
			changes.add(new BlockChange(loc, this.genchanges.get(loc)));
			i++;
			if (i > 200) {
				i = 0;
				try {
					Thread.sleep(200L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		gens.add(new OldGeneration(this.title, this.creator, changes));
	}
	
	public class BlockChange
	{
		public Location loc;
		public MaterialData oldBlock;
		public MaterialData newBlock;
		public Object data;
		
		@SuppressWarnings("deprecation")
		public BlockChange(Location location, MaterialData block)
		{
			this.loc = location;
			this.oldBlock = location.getBlock().getState().getData();
			this.newBlock = block;
			
			loc.getBlock().setType(block.getItemType());
			loc.getBlock().setData(block.getData());
		}
		
		@SuppressWarnings("deprecation")
		public void revert()
		{
			this.loc.getBlock().setType(this.oldBlock.getItemType());
			this.loc.getBlock().setData(this.oldBlock.getData());
		}
	}
	
	public class OldGeneration
	{
		public List<BlockChange> changes;
		public Player player;
		
		public OldGeneration(String title, Player player, List<BlockChange> changes) 
		{
			this.changes = changes;
			this.player = player;
		}
		
		/**Revert the old generation*/
		public void revert()
		{
			this.player.sendMessage(ChatColor.LIGHT_PURPLE + "" + changes.size() + " blocks reverted!");
			
			int i = 0;
			for (BlockChange bc : changes) {
				bc.revert();
				i++;
				if (i > 200) {
					i = 0;
					try {
						Thread.sleep(200L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void popOldGeneration()
	{
		gens.get(gens.size() - 1).revert();
		gens.remove(gens.size() - 1);
	}

}
