package com.projectkorra.spirits.ability;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.spirits.SpiritElement;

import org.bukkit.entity.Player;

public abstract class SpiritAbility extends CoreAbility {

	public SpiritAbility(Player player) {
		super(player);
	}
	
	@Override
	public Element getElement() {
		return SpiritElement.SPIRIT;
	}
}