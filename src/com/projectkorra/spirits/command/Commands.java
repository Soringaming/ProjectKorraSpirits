package com.projectkorra.spirits.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.projectkorra.spirits.ProjectKorraSpirits;

public class Commands {
	
	private ProjectKorraSpirits plugin;
	
	// Integers
	public static int startingNumber;
	public static int currentNumber;
	public static int clockTask;
	// Booleans
	public static Boolean arenainuse;

	public static Set<Player> pbChat = new HashSet<Player>();
	public static HashMap<String, LinkedList<String>> teamInvites = new HashMap<String, LinkedList<String>>();
	public static HashMap<Player, ItemStack[]> tmpArmor = new HashMap<Player, ItemStack[]>();
	
	public Commands(ProjectKorraSpirits plugin) {
		this.plugin = plugin;
		init();
	}

	private void init() {
		PluginCommand pbcmd = plugin.getCommand("spirits");

		//Base Commands
		new HelpCommand();
		new SpawnCommand();
		new SpiritWorldCommand();

		/**
		 * Set of all of the Classes which extend Command
		 */

		CommandExecutor exe;

		exe = new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
				for (int i = 0; i < args.length; i++) {
					args[i] = args[i];
				}

				if (args.length == 0) {
					new HelpCommand().execute(s, Arrays.asList(args));
					return true;
				}

				List<String> sendingArgs = Arrays.asList(args).subList(1, args.length);
				for (SpiritsCommand command : SpiritsCommand.instances.values()) {
					if (command.isChild() && args.length > 1) {
						if (Arrays.asList(command.getParentAliases()).contains(args[0].toLowerCase())
								&& Arrays.asList(command.getAliases()).contains(args[1].toLowerCase())) {
							command.execute(s, sendingArgs);
							return true;
						}
					}
					if (command.isParent() && args.length > 1) {
						if (!command.isNumeric(args[1]))
							continue;
					}

					if (Arrays.asList(command.getAliases()).contains(args[0].toLowerCase())) {
						command.execute(s, sendingArgs);
						return true;
					}
				}
				return true;
			}
		};
		pbcmd.setExecutor(exe);
	}

}
