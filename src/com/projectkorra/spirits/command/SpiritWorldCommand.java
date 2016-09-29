package com.projectkorra.spirits.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.projectkorra.spirits.SpiritMethods;
import com.projectkorra.spirits.configuration.ConfigManager;

public class SpiritWorldCommand extends SpiritsCommand {

	public SpiritWorldCommand() {
		super("spiritworld", "/spirits spiritworld <args>", "Command for Spirit World", new String[] {"spiritworld", "spiritw", "sworld", "sw"});
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) {
			return;
		}
		
		Player player = (Player) sender;
		
		if (!SpiritMethods.isSpiritWorldEnabled()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.SpiritWorldDisabled")));
			return;
		}
		
		if (args.size() < 1) {

			player.sendMessage("not enough args");
			player.sendMessage("/spirits spiritworld generate");
			player.sendMessage("/spirits spiritworld generateportal");
			
		} else {
			if (args.get(0).equalsIgnoreCase("generate")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.SpiritWorldGenerating")));
				if (Bukkit.getWorld(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName")) != null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.SpiritWorldAlreadyExists")));
				} else {
					World spiritworld = Bukkit.createWorld(new WorldCreator(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName")));
					player.teleport(spiritworld.getSpawnLocation());
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.SpiritWorldGenerated")));
					player.sendMessage(ChatColor.GRAY + "[NOTE] If you wish to hook this world into a third-party multi-world plugin, you will need to import it."
							+ "\n(For MultiVerse: /mv import " + spiritworld.getName() + ")");
				}
			} else if (args.get(0).equalsIgnoreCase("generateportal")) {
				sender.sendMessage("Generating Spirit World Portal.");
			} else {
				sender.sendMessage("Invalid args");
			}
		}
	}

}
