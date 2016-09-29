package com.projectkorra.spirits.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SpiritsCommand {
	
	String[] teamround = {"team", "round"};
	
	public HelpCommand() {
		super ("help", "/spirits help [Topic|Page]", "Displays help for PKSpirits.", new String[] {"help", "h"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) {
			return;
		}
		if (args.size() == 0 || isNumeric(args.get(0))) {
			int page = 1;
			if (args.size() > 1) {
				page = Integer.parseInt(args.get(0));
			}

			List<String> strings = new ArrayList<String>();
			for (SpiritsCommand command : instances.values()) {
				if (!command.getName().equalsIgnoreCase("help") && !command.isChild()
						&& sender.hasPermission("spirits.command." + command.getName())) {
					strings.add(command.getProperUse());
				}
			}
			Collections.sort(strings);
			Collections.reverse(strings);
			strings.add(instances.get("help").getProperUse());
			Collections.reverse(strings);
			for (String s : getPage(strings, ChatColor.GOLD + "Commands: <Required> [Optional]", page, false)) {
				sender.sendMessage(ChatColor.BLUE + s);
			}
			return;
		}

		String arg = args.get(0);

		if (instances.keySet().contains(arg.toLowerCase())) {
			instances.get(arg).help(sender, true);
		}
	}

}
