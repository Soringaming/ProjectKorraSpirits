package com.projectkorra.spirits.listener;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.util.MultiAbilityManager;
import com.projectkorra.projectkorra.airbending.Suffocate;
import com.projectkorra.projectkorra.chiblocking.ChiCombo;
import com.projectkorra.projectkorra.chiblocking.Paralyze;
import com.projectkorra.projectkorra.waterbending.Bloodbending;
import com.projectkorra.spirits.ProjectKorraSpirits;
import com.projectkorra.spirits.SpiritElement;
import com.projectkorra.spirits.ability.SpiritAbility;
import com.projectkorra.spirits.ability.dark.DarkBeam;
import com.projectkorra.spirits.ability.neutral.SpiritFlight;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;

import java.util.Set;

public class AbilityListener implements Listener {
	
	ProjectKorraSpirits plugin;
	
	public AbilityListener(ProjectKorraSpirits plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true) 
	public void onPlayerSwing(PlayerAnimationEvent event) {
		if (event.isCancelled()) return;

		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		if (bPlayer == null) {
			return;
		}
		
		if (Suffocate.isBreathbent(player)) {
			event.setCancelled(true);
			return;
		} else if (Bloodbending.isBloodbent(player) || Paralyze.isParalyzed(player) || ChiCombo.isParalyzed(player)) {
			event.setCancelled(true);
			return;
		} else if (bPlayer.isChiBlocked()) {
			event.setCancelled(true);
			return;
		} else if (GeneralMethods.isInteractable(player.getTargetBlock((Set<Material>)null, 5))) {
			event.setCancelled(true);
			return;
		}
		
		CoreAbility coreAbil = bPlayer.getBoundAbility();
		if (coreAbil == null && !(coreAbil instanceof SpiritAbility) && !MultiAbilityManager.hasMultiAbilityBound(player)) {
			return;
		}
		
		if (bPlayer.canBendIgnoreCooldowns(coreAbil) && bPlayer.isElementToggled(SpiritElement.SPIRIT) == true) {
			if (coreAbil.getName().equalsIgnoreCase("SpiritFlight")) {
				SpiritFlight.setFlying(player);
			}
			if (coreAbil.getName().equalsIgnoreCase("DarkBeam")) {
				new DarkBeam(player);
			}
		}
		
	}

}
