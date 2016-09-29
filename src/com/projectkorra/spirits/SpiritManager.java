package com.projectkorra.spirits;

import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.spirits.spiritmob.DarkSpirit;
import com.projectkorra.spirits.spiritmob.Spirit;

public class SpiritManager extends BukkitRunnable {
	
	@Override
	public void run() {
		
		for (Spirit spirit : Spirit.SPIRITS.values()) {
			if (spirit instanceof DarkSpirit) {
				DarkSpirit darkSpirit = (DarkSpirit) spirit;
				LivingEntity dsEntity = darkSpirit.getEntity();
				if (dsEntity != null && !dsEntity.isDead()) {
					ParticleEffect.SMOKE.display(dsEntity.getLocation(), 0.2F, 0.2F, 0.2F, 0.025F, 10);
				}
			}
		}

	}

}
