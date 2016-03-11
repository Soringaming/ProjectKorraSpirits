package com.projectkorra.spirits.ability;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.SubAbility;
import com.projectkorra.spirits.SpiritElement;

import org.bukkit.entity.Player;

public abstract class LightAbility extends CoreAbility implements SubAbility {

	public LightAbility(Player player) {
		super(player);
	}
	
	@Override
	public Class<? extends Ability> getParentAbility() {
		return SpiritAbility.class;
	}
	
	@Override
	public Element getElement() {
		return SpiritElement.LIGHT;
	}
}
