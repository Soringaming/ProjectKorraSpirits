package com.projectkorra.spirits.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.projectkorra.spirits.spiritmob.DarkSpirit;

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
		
		DarkSpirit darkSpirit = new DarkSpirit();
		darkSpirit.spawn(player.getLocation());
		LivingEntity dsEntity = darkSpirit.getEntity();
		dsEntity.setSilent(true);
	}
	
}
