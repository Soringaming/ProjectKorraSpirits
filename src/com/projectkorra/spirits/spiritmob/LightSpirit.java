package com.projectkorra.spirits.spiritmob;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EntitySheep;
import net.minecraft.server.v1_10_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_10_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_10_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_10_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_10_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_10_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_10_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_10_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_10_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;

public class LightSpirit extends EntitySheep {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LightSpirit(World world) {
		super(((CraftWorld) world).getHandle());
		
		Set goalB = (Set) SpiritMobUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
		Set goalC = (Set) SpiritMobUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
		Set targetB = (Set) SpiritMobUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
		Set targetC = (Set) SpiritMobUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, false));
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, DarkSpirit.class, true));
		
		this.setCustomName(ChatColor.WHITE + "Light Spirit");
		this.setCustomNameVisible(true);
    }

}