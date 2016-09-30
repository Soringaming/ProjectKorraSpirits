package com.projectkorra.spirits.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

	public static Config defaultConfig;
	public static Config dataConfig;
	
	public ConfigManager() {
		defaultConfig = new Config(new File("config.yml"));
		dataConfig = new Config(new File("data.yml"));
		setupConfig();
		setupData();
	}
	
	public static void setupConfig() {
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
		config.addDefault("Properties.Chat.Messages.InsufficientArguments", "&cNot enough arguments.");
		config.addDefault("Properties.Chat.Messages.SpiritWorldAlreadyExists", "&cThe Spirit World already exists. If you wish to regenerate the Spirit World, delete the 'spiritworld' world, and try this command again.");
		config.addDefault("Properties.Chat.Messages.SpiritWorldGenerating", "&aAttempting to generate Spirit World...");
		config.addDefault("Properties.Chat.Messages.SpiritWorldGenerated", "&aSpirit World successfully generated.");
		config.addDefault("Properties.Chat.Messages.PortalGenerating", "&aAttempting to generate the {PORTAL} Spirit Portal...");
		config.addDefault("Properties.Chat.Messages.PortalGenerated", "&a{PORTAL} Spirit Portal successfully generated.");
		config.addDefault("Properties.Chat.Messages.InvalidPortal", "&c{PORTAL} is not a valid portal.");
		config.addDefault("Properties.Chat.Messages.PortalAlreadyExists", "&cYou were unable to generate the {PORTAL} Spirit Portal because it already exists.");

		Collection<String> darkspiritDrops = new ArrayList<String>();
		darkspiritDrops.add("GOLD_INGOT:3");
		
		//config.addDefault("Properties.Spirits." + SpiritType.DARK_SPIRIT.toString() + ".Enabled", Boolean.valueOf(true));
		//config.addDefault("Properties.Spirits." + SpiritType.DARK_SPIRIT.toString() + ".Drops", darkspiritDrops);
		
		config.addDefault("Properties.SpiritWorld.Enabled", Boolean.valueOf(true));
		config.addDefault("Properties.SpiritWorld.WorldName", "spiritworld");
		config.addDefault("Properties.SpiritWorld.RegionProtected", Boolean.valueOf(true));
		
		defaultConfig.save();
	}
	
	public static void setupData() {
		FileConfiguration config;
		config = dataConfig.get();

		config.set("SpiritWorld.WorldName", getConfig().getString("Properties.SpiritWorld.WorldName"));
		config.addDefault("SpiritWorld.SpawnLocation.x", Double.valueOf(100));
		config.addDefault("SpiritWorld.SpawnLocation.y", Double.valueOf(100));
		config.addDefault("SpiritWorld.SpawnLocation.z", Double.valueOf(100));
		config.addDefault("SpiritWorld.SpawnLocation.yaw", Double.valueOf(100));
		config.addDefault("SpiritWorld.SpawnLocation.pitch", Double.valueOf(100));
		
		config.addDefault("Portals.SpiritWorld.Northern.Valid", Boolean.valueOf(false));
		config.addDefault("Portals.SpiritWorld.Northern.Location.x", "INVALID");
		config.addDefault("Portals.SpiritWorld.Northern.Location.y", "INVALID");
		config.addDefault("Portals.SpiritWorld.Northern.Location.z", "INVALID");
		config.addDefault("Portals.SpiritWorld.Northern.Location.yaw", "INVALID");
		config.addDefault("Portals.SpiritWorld.Northern.Location.pitch", "INVALID");

		config.addDefault("Portals.SpiritWorld.Southern.Valid", Boolean.valueOf(false));
		config.addDefault("Portals.SpiritWorld.Southern.Location.x", "INVALID");
		config.addDefault("Portals.SpiritWorld.Southern.Location.y", "INVALID");
		config.addDefault("Portals.SpiritWorld.Southern.Location.z", "INVALID");
		config.addDefault("Portals.SpiritWorld.Southern.Location.yaw", "INVALID");
		config.addDefault("Portals.SpiritWorld.Southern.Location.pitch", "INVALID");
		
		config.addDefault("Overworld.WorldName", "INVALID");

		config.addDefault("Portals.Overworld.Northern.Valid", Boolean.valueOf(false));
		config.addDefault("Portals.Overworld.Northern.Location.x", "INVALID");
		config.addDefault("Portals.Overworld.Northern.Location.y", "INVALID");
		config.addDefault("Portals.Overworld.Northern.Location.z", "INVALID");
		config.addDefault("Portals.Overworld.Northern.Location.yaw", "INVALID");
		config.addDefault("Portals.Overworld.Northern.Location.pitch", "INVALID");

		config.addDefault("Portals.Overworld.Southern.Valid", Boolean.valueOf(false));
		config.addDefault("Portals.Overworld.Southern.Location.x", "INVALID");
		config.addDefault("Portals.Overworld.Southern.Location.y", "INVALID");
		config.addDefault("Portals.Overworld.Southern.Location.z", "INVALID");
		config.addDefault("Portals.Overworld.Southern.Location.yaw", "INVALID");
		config.addDefault("Portals.Overworld.Southern.Location.pitch", "INVALID");
		
		
		dataConfig.save();
	}
	
	public static FileConfiguration getConfig() {
		return ConfigManager.defaultConfig.get();
	}
	
	public static FileConfiguration getData() {
		return ConfigManager.dataConfig.get();
	}
}