package com.projectkorra.spirits.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.projectkorra.spirits.spiritmob.SpiritType;

import net.md_5.bungee.api.ChatColor;

public class ListCommand extends SpiritsCommand {
	
	public ListCommand() {
		super ("list", "/spirits list", "Lists all available spirits.", new String[] {"list", "l"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 0)) {
			return;
		}
		
		Player player = (Player) sender;
		
		for (SpiritType spirit : SpiritType.values()) {
			player.sendMessage(ChatColor.BLUE + spirit.getSpiritName());
		}
	}

}
