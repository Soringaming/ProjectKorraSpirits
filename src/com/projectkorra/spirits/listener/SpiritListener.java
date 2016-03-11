package com.projectkorra.spirits.listener;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent.Result;
import com.projectkorra.spirits.ProjectKorraSpirits;
import com.projectkorra.spirits.SpiritElement;
import com.projectkorra.spirits.SpiritMethods;
import com.projectkorra.spirits.SpiritPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpiritListener implements Listener {

	ProjectKorraSpirits plugin;
	
	public SpiritListener(ProjectKorraSpirits plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		SpiritMethods.createSpiritPlayer(event.getPlayer().getUniqueId(), event.getPlayer().getName());
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onElementChange(PlayerChangeElementEvent event) {
		Element element = event.getElement();
		Result result = event.getResult();
		if (element.equals(SpiritElement.SPIRIT)) {
			if (result.equals(Result.CHOOSE) || result.equals(Result.ADD)) {
				SpiritMethods.chooseSpirit(event.getTarget());
			} else if (result.equals(Result.PERMAREMOVE) || result.equals(Result.REMOVE)) {
				SpiritPlayer sPlayer = SpiritPlayer.getSpiritPlayer(event.getTarget());
				sPlayer.removeSpirit();
			}
		}
	}
}
