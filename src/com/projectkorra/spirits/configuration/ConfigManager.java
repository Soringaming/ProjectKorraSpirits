package com.projectkorra.spirits.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigManager {

	public static Config defaultConfig;
	
	public ConfigManager() {
		defaultConfig = new Config(new File("config.yml"));
		init();
	}
	
	public static void init() {
		FileConfiguration config;
		config = defaultConfig.get();
		
		config.addDefault("Chat.Colors.Spirit", "BLUE");
		config.addDefault("Chat.Colors.SpiritSub", "BLUE");
		
		config.addDefault("Storage.engine", "sqlite");

		config.addDefault("Storage.MySQL.host", "localhost");
		config.addDefault("Storage.MySQL.port", 3306);
		config.addDefault("Storage.MySQL.pass", "");
		config.addDefault("Storage.MySQL.db", "minecraft");
		config.addDefault("Storage.MySQL.user", "root");
		
		defaultConfig.save();
	}
	
	public static FileConfiguration getConfig() {
		return ConfigManager.defaultConfig.get();
	}
}
