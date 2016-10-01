package com.projectkorra.spirits;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.spirits.command.Commands;
import com.projectkorra.spirits.configuration.ConfigManager;
import com.projectkorra.spirits.listener.AbilityListener;
import com.projectkorra.spirits.listener.EntityDamageByEntity;
import com.projectkorra.spirits.listener.EntityDeath;
import com.projectkorra.spirits.listener.PlayerChangedWorld;
import com.projectkorra.spirits.listener.PlayerJoin;
import com.projectkorra.spirits.listener.SpiritDamage;
import com.projectkorra.spirits.listener.SpiritListener;
import com.projectkorra.spirits.spiritworld.SpiritWorldGenerator;
import com.projectkorra.spirits.storage.DBConnection;

public class ProjectKorraSpirits extends JavaPlugin {
	
	/*
	 *  Good Spirits Suggestions
	 *  
	 *  http://projectkorra.com/threads/spiritual-energy-levels.2293/
	 *  http://projectkorra.com/threads/wan-shi-tongs-spirit-library.4433/    - Not necessarily the library, but maybe other iconic structures
	 *  http://projectkorra.com/threads/summonallies.4086/
	 *  http://projectkorra.com/threads/rejuvenate-syphon.4089/
	 *  
	 *  
	 *  Maybe Suggestions
	 *  
	 *  http://projectkorra.com/threads/light-spirit-ability-lightflash-and-glide.4409/
	 *  http://projectkorra.com/threads/dark-spirit-ability-darkflash-and-descent.4408/
	 *  
	 * 
	 */

	public static ProjectKorraSpirits plugin;
	public static Logger log;
	public static BossBar BAR = Bukkit.createBossBar(ChatColor.BLUE + "" + ChatColor.BOLD + "Spirit World", BarColor.PURPLE, BarStyle.SOLID);
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		plugin = this;
		ProjectKorraSpirits.log = this.getLogger();
		
		new ConfigManager();
		new Commands(this);
		
		DBConnection.host = getConfig().getString("Storage.MySQL.host");
		DBConnection.port = getConfig().getInt("Storage.MySQL.port");
		DBConnection.pass = getConfig().getString("Storage.MySQL.pass");
		DBConnection.db = getConfig().getString("Storage.MySQL.db");
		DBConnection.user = getConfig().getString("Storage.MySQL.user");
		DBConnection.init();
		if (DBConnection.isOpen() == false) {
			return;
		}
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			SpiritMethods.createSpiritPlayer(player.getUniqueId(), player.getName());
		}
		
		CoreAbility.registerPluginAbilities(plugin, "com.projectkorra.spirits.ability");
		getServer().getPluginManager().registerEvents(new AbilityListener(this), this);
		getServer().getPluginManager().registerEvents(new SpiritListener(this), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new SpiritManager(), 0, 1);
		
		registerEvents();
		
		if (Bukkit.getWorld(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName")) != null) {
			World spiritWorld = Bukkit.createWorld(new WorldCreator(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName")));
			for (Player player : spiritWorld.getPlayers()) {
				BAR.addPlayer(player);
			}
		}
	}
	
	@Override
	public void onDisable() {
		if (DBConnection.isOpen != false) {
			DBConnection.sql.close();
		}
		
		for (Player player : BAR.getPlayers()) {
			BAR.removePlayer(player);
		}
	}
	
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new EntityDamageByEntity(), this);
		pm.registerEvents(new EntityDeath(), this);
		pm.registerEvents(new SpiritDamage(), this);
		pm.registerEvents(new PlayerChangedWorld(), this);
		pm.registerEvents(new PlayerJoin(), this);
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String genId) {
		
		return new SpiritWorldGenerator();
	}
}
