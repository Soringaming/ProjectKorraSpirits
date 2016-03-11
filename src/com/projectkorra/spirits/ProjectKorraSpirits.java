package com.projectkorra.spirits;

import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.spirits.configuration.ConfigManager;
import com.projectkorra.spirits.listener.AbilityListener;
import com.projectkorra.spirits.listener.SpiritListener;
import com.projectkorra.spirits.storage.DBConnection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ProjectKorraSpirits extends JavaPlugin {

	public static ProjectKorraSpirits plugin;
	public static Logger log;
	
	@Override
	public void onEnable() {
		plugin = this;
		ProjectKorraSpirits.log = this.getLogger();
		
		new ConfigManager();
		
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
	}
	
	@Override
	public void onDisable() {
		if (DBConnection.isOpen != false) {
			DBConnection.sql.close();
		}
	}
}
