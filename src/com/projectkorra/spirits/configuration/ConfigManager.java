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
		
		config.addDefault("Storage.engine", "sqlite");

		config.addDefault("Storage.MySQL.host", "localhost");
		config.addDefault("Storage.MySQL.port", 3306);
		config.addDefault("Storage.MySQL.pass", "");
		config.addDefault("Storage.MySQL.db", "minecraft");
		config.addDefault("Storage.MySQL.user", "root");

		config.addDefault("Properties.Chat.Colors.Spirit", "BLUE");
		config.addDefault("Properties.Chat.Colors.SpiritSub", "BLUE");
		
		config.addDefault("Properties.Chat.Messages.SpiritWorldDisabled", "&cThe Spirit World has been disabled via plugin configuration.");
		config.addDefault("Properties.Chat.Messages.InsufficientPermissions", "&cYou don't have permission to do that.");
		config.addDefault("Properties.Chat.Messages.SpiritWorldAlreadyExists", "&cThe Spirit World already exists. If you wish to regenerate the Spirit World, delete the 'spiritworld' world, and try this command again.");
		config.addDefault("Properties.Chat.Messages.SpiritWorldGenerating", "&aAttempting to generate Spirit World...");
		config.addDefault("Properties.Chat.Messages.SpiritWorldGenerated", "&aSpirit World successfully generated.");
		
		config.addDefault("Properties.SpiritWorld.Enabled", Boolean.valueOf(true));
		config.addDefault("Properties.SpiritWorld.WorldName", "spiritworld");
		config.addDefault("Properties.SpiritWorld.RegionProtected", Boolean.valueOf(true));
		
		defaultConfig.save();
	}
	
	public static FileConfiguration getConfig() {
		return ConfigManager.defaultConfig.get();
	}
}