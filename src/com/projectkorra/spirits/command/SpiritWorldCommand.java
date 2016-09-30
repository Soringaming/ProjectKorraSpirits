package com.projectkorra.spirits.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.projectkorra.spirits.Generation.OldGeneration;
import com.projectkorra.spirits.SpiritMethods;
import com.projectkorra.spirits.configuration.ConfigManager;
import com.projectkorra.spirits.spiritworld.SpiritPortal;

public class SpiritWorldCommand extends SpiritsCommand {
	
	public static List<OldGeneration> prevChanges = new ArrayList<OldGeneration>();

	public SpiritWorldCommand() {
		super("spiritworld", "/spirits spiritworld <args>", "Command for Spirit World", new String[] {"spiritworld", "spiritw", "sworld", "sw"});
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 3)) {
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
			player.sendMessage("/spirits spiritworld portal <generate/destroy> <northern/southern>");
			
		} else {
			if (args.get(0).equalsIgnoreCase("generate")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.SpiritWorldGenerating")));
				if (Bukkit.getWorld(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName")) != null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.SpiritWorldAlreadyExists")));
					player.teleport(Bukkit.getWorld(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName")).getSpawnLocation());
				} else {
					World spiritworld = Bukkit.createWorld(new WorldCreator(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName")));
					player.teleport(spiritworld.getSpawnLocation());
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.SpiritWorldGenerated")));
					player.sendMessage(ChatColor.GRAY + "[NOTE] If you wish to hook this world into a third-party multi-world plugin, you will need to import it."
							+ "\n(For MultiVerse: /mv import " + spiritworld.getName() + ")");
				}
			} else if (args.get(0).equalsIgnoreCase("portal")) {
				if (args.size() < 3) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.InsufficientArguments")));
				} else {
					if (args.get(1).equalsIgnoreCase("generate")) {
						
						boolean spiritWorld = SpiritMethods.isSpiritWorld(player.getWorld()) ? true : false;
						if (args.get(2).equalsIgnoreCase("northern")) {

							player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.PortalGenerating").replace("{PORTAL}", "Northern")));
							
							if (SpiritMethods.portalExists(spiritWorld, true)) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.PortalAlreadyExists").replace("{PORTAL}", "Northern")));
								return;
							}
							
							new SpiritPortal(player, player.getLocation(), (byte) 9, (byte) 11).generate();
							SpiritMethods.setPortalLocation(player.getLocation(), spiritWorld, true);
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.PortalGenerated").replace("{PORTAL}", "Northern")));
							
						} else if (args.get(2).equalsIgnoreCase("southern")) {

							player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.PortalGenerating").replace("{PORTAL}", "Southern")));
							
							if (SpiritMethods.portalExists(spiritWorld, false)) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.PortalAlreadyExists").replace("{PORTAL}", "Southern")));
								return;
							}
							
							new SpiritPortal(player, player.getLocation(), (byte) 11, (byte) 9).generate();
							SpiritMethods.setPortalLocation(player.getLocation(), spiritWorld, false);
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("Properties.Chat.Messages.PortalGenerated").replace("{PORTAL}", "Southern")));
							
						} else {
							
							player.sendMessage(ChatColor.RED + args.get(2) + " is not a valid portal.");
						}
						
					} else if (args.get(1).equalsIgnoreCase("destroy")) {
						
						if (args.get(2).equalsIgnoreCase("northern")) {
							
							player.sendMessage(ChatColor.DARK_PURPLE + "TODO - Destroy Northern Spirit Portal...");
							
						} else if (args.get(2).equalsIgnoreCase("southern")) {

							player.sendMessage(ChatColor.DARK_PURPLE + "TODO - Destroy Southern Spirit Portal...");
							
						} else {

							player.sendMessage(ChatColor.RED + args.get(2) + " is not a valid portal.");
						}
						
					}
				}

			} else {
				player.sendMessage("Invalid args");
			}
		}
	}

}
