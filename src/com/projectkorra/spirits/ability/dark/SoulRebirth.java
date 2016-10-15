package com.projectkorra.spirits.ability.dark;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.spirits.SpiritMethods;
import com.projectkorra.spirits.ability.DarkAbility;
import com.projectkorra.spirits.configuration.ConfigManager;
import com.projectkorra.spirits.spiritmob.DarkSpirit;
import com.projectkorra.spirits.spiritmob.SpiritType;

public class SoulRebirth extends DarkAbility {
	
	private long cooldown;
	private double range;
	private double duration;
	
	private Location origin;
	private LivingEntity target;
	private long chargeTime;
	private long spawnTime;
	private boolean spawned;
	private List<DarkSpirit> spirits = new ArrayList<DarkSpirit>();

	public SoulRebirth(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			cancel();
			return;
		}
		
		setFields();
		this.origin = player.getLocation();
		start();
	}
	
	public void setFields() {
		
		this.cooldown = ConfigManager.getConfig().getLong("Abilities.Dark.SoulRebirth.Cooldown");
		this.range = ConfigManager.getConfig().getDouble("Abilities.Dark.SoulRebirth.Range");
		this.duration = ConfigManager.getConfig().getDouble("Abilities.Dark.SoulRebirth.Duration");
	}

	@Override
	public void progress() {
		
		if (player == null || !player.isOnline() || player.isDead()) {
			cancel();
			return;
		}
		
		if (!bPlayer.canBendIgnoreCooldowns(this)) {
			cancel();
			return;
		}
		
		if (spawned) {
			if (System.currentTimeMillis() - spawnTime > duration) {
				
				cancel();
				return;
			}
		} else {
			
			if (!player.isSneaking()) {
				cancel();
				return;
			}
			
			int option = System.currentTimeMillis() - startTime < 1000 ? 1 :
				System.currentTimeMillis() - startTime < 2000 ? 2 :
					System.currentTimeMillis() - startTime < 3000 ? 3 :
						System.currentTimeMillis() - startTime < 4000 ? 4 : 5;
			Location point = player.getLocation().clone().add(player.getLocation().getDirection()).add(0, 1.5, 0);
			
			switch (option) { // http://trycolors.com/
				case 1:
					GeneralMethods.displayColoredParticle(point, "000000"); // 100% black 0% white
					break;
				case 2:
					GeneralMethods.displayColoredParticle(point, "404040"); // 75% black 25% white
					break;
				case 3:
					GeneralMethods.displayColoredParticle(point, "808080");	// 50% black 50% white
					break;
				case 4:
					GeneralMethods.displayColoredParticle(point, "bfbfbf");	// 25% black 75% white
					break;
				case 5:
					GeneralMethods.displayColoredParticle(point, "ffffff");	// 0% black 100% white
					break;
			}

			ParticleEffect.WITCH_MAGIC.display(player.getLocation(), 3, 1, 3, 0, 1);
			ParticleEffect.SMOKE.display(player.getLocation(), 3, 1, 3, 0, 2);
		}
	}
	
	public void registerClick() {
		
		if (CoreAbility.getAbility(player, SoulRebirth.class) == null) {
			cancel();
			return;
		}
		
		Entity target = GeneralMethods.getTargetedEntity(player, range);
		if (target != null && target instanceof LivingEntity) {
			this.target = (LivingEntity) target;
			chargeTime = System.currentTimeMillis() - startTime;
			
			if (chargeTime < 1000) {
				cancel();
				return;
			} else if (chargeTime < 5000) {
				spawn((int) Math.floor(chargeTime / 1000));
			} else {
				spawn(5);	
			}
		}
	}
	
	public void spawn(int amount) {
		this.bPlayer.addCooldown(this);
		this.spawned = true;
		this.spawnTime = System.currentTimeMillis();
		
		for (int i = 0; i < amount; i++) {
			
			List<Block> blocks = GeneralMethods.getBlocksAroundPoint(player.getLocation(), 4);
			int value = SpiritMethods.randomInteger(0, blocks.size() - 1);
			Location location = GeneralMethods.getTopBlock(blocks.get(value).getLocation(), 4, 4).getLocation();
			Location spawnPoint = location.clone().add(0, 1, 0);
			DarkSpirit darkSpirit = new DarkSpirit(spawnPoint.getWorld());
			SpiritType.spawnEntity(darkSpirit, spawnPoint);
			if (target != null) {
				Creature spider = (Creature) darkSpirit.getBukkitEntity();
				spider.setTarget(this.target);
			}
			location.getWorld().playSound(location, Sound.ENTITY_WITHER_SHOOT, 0.25F, 0.1F);
			spirits.add(darkSpirit);
		}
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return origin;
	}

	@Override
	public String getName() {
		return "SoulRebirth";
	}
	
	@Override
	public String getDescription() {
		return "SoulRebirth is an ability for Dark Spirits, enabling them to summon spiritual allies. "
				+ "To use, sneak (default: Shift) to charge the ability. Once ready to activate, punch (default: L.Click). "
				+ "The number of spirits spawned is dependent on how long you charge the ability for.";
	}

	@Override
	public boolean isExplosiveAbility() {
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isIgniteAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}
	
	public void cancel() {
		
		for (DarkSpirit darkSpirit : spirits) {
			darkSpirit.getBukkitEntity().remove();
		}
		
		spirits.clear();
		
		remove();
	}

}
