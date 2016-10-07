package com.projectkorra.spirits.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.projectkorra.spirits.spiritmob.DarkSpirit;
import com.projectkorra.spirits.spiritmob.LightSpirit;
import com.projectkorra.spirits.spiritmob.SpiritType;

import net.md_5.bungee.api.ChatColor;

public class SpawnCommand extends SpiritsCommand {
	
	public SpawnCommand() {
		super ("spawn", "/spirits spawn <spirit>", "Spawn a spirit.", new String[] {"spawn", "s"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) {
			return;
		}
		
		Player player = (Player) sender;
		
		if (args.size() < 1) {
			player.sendMessage(ChatColor.RED + "Please specify a spirit. " + ChatColor.DARK_RED + "/spirits spawn <spirit>");
			return;
		}
		
		String given = args.get(0);
		for (SpiritType spirit : SpiritType.values()) {
			if (given.equalsIgnoreCase(spirit.getSpiritName())) {
				if (spirit == SpiritType.DARK_SPIRIT) {
					
					DarkSpirit darkSpirit = new DarkSpirit(player.getWorld());
					SpiritType.spawnEntity(darkSpirit, player.getLocation());
				}
				
				if (spirit == SpiritType.LIGHT_SPIRIT) {
					
					LightSpirit lightSpirit = new LightSpirit(player.getWorld());
					SpiritType.spawnEntity(lightSpirit, player.getLocation());
				}
			}
			
		}
	}
	
}
