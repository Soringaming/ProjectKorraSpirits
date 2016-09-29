package com.projectkorra.spirits;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.LivingEntity;
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
import com.projectkorra.spirits.listener.SpiritDamage;
import com.projectkorra.spirits.listener.SpiritListener;
import com.projectkorra.spirits.spiritmob.Spirit;
import com.projectkorra.spirits.spiritworld.SpiritWorldGenerator;
import com.projectkorra.spirits.storage.DBConnection;

public class ProjectKorraSpirits extends JavaPlugin {

	public static ProjectKorraSpirits plugin;
	public static Logger log;
	
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
			Bukkit.createWorld(new WorldCreator(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName")));
		}
	}
	
	@Override
	public void onDisable() {
		if (DBConnection.isOpen != false) {
			DBConnection.sql.close();
		}
		
		for (Spirit spirit : Spirit.SPIRITS.values()) {
			LivingEntity sEntity = spirit.getEntity();
			sEntity.remove(); // Despawning all Spirits on disable until spirits don't get lost on reload.
		}
	}
	
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new EntityDamageByEntity(), this);
		pm.registerEvents(new EntityDeath(), this);
		pm.registerEvents(new SpiritDamage(), this);
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String genId) {
		
		return new SpiritWorldGenerator();
	}
}
