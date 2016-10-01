package com.projectkorra.spirits;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.spirits.configuration.ConfigManager;
import com.projectkorra.spirits.storage.DBConnection;

public class SpiritMethods {

	static Random rand = new Random();
	/**
	 * Creates a {@link SpiritPlayer} with the data from the database. This
	 * runs when a player logs in.
	 * 
	 * @param player The player
	 * @throws SQLException
	 */
	public static void createSpiritPlayer(final UUID uuid, final String player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				createSpiritPlayerAsync(uuid, player);
			}
		}.runTaskAsynchronously(ProjectKorra.plugin);
	}
	
	public static void createSpiritPlayerAsync(final UUID uuid, final String player) {
		ResultSet rs = DBConnection.sql.readQuery("SELECT * FROM spirit_players WHERE uuid = '" + uuid.toString() + "'");
		try {
			if (!rs.next()) {
				new SpiritPlayer(uuid, player, null);
				DBConnection.sql.modifyQuery("INSERT INTO spirit_players (uuid, player) VALUES ('" + uuid.toString() + "', '" + player + "')");
			} else {
				// The player has at least played before.
				String storedName = rs.getString("player");
				if (!player.equalsIgnoreCase(storedName)) {
					DBConnection.sql.modifyQuery("UPDATE spirit_players SET player = '" + player + "' WHERE uuid = '" + uuid.toString() + "'");
					// They have changed names.
					ProjectKorra.log.info("Updating Player Name for " + player);
				}
				String spiritType = rs.getString("spirit");
				SubElement spirit = null;
				if (spiritType != null) {
					if (spiritType.equalsIgnoreCase("l")) {
						spirit = SpiritElement.LIGHT;
					}
					if (spiritType.equalsIgnoreCase("d")) {
						spirit = SpiritElement.DARK;
					}
				}
				final SubElement spirit2 = spirit;
				new BukkitRunnable() {
					@Override
					public void run() {
						new SpiritPlayer(uuid, player, spirit2);
					}
				}.runTask(ProjectKorra.plugin);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void chooseSpirit(Player player) {
		SpiritPlayer sPlayer = SpiritPlayer.getSpiritPlayer(player);
		if (rand.nextInt(2) == 0) {
			sPlayer.setLightSpirit();
			player.sendMessage(SpiritElement.LIGHT.getColor() + "You have become a Light Spirit!");
		} else {
			sPlayer.setDarkSpirit();
			player.sendMessage(SpiritElement.DARK.getColor() + "You have become a Dark Spirit!");
		}
	}
	
	public static boolean isSpiritWorldEnabled() {
		
		return ConfigManager.getConfig().getBoolean("Properties.SpiritWorld.Enabled");
	}
	
	public static boolean isSpiritWorld(World world) {
		
		return world.getName().equalsIgnoreCase(ConfigManager.getConfig().getString("Properties.SpiritWorld.WorldName"));
	}
	
	public static Location getPortalLocation(boolean spiritWorld, boolean northern) {
		
		String worldPath = spiritWorld ? "SpiritWorld" : "Overworld";
		String portalPath = northern ? "Northern" : "Southern";
		
		if (!portalExists(spiritWorld, northern)) {
			return null;
		}
		
		World world = Bukkit.getWorld(ConfigManager.getData().getString(worldPath + ".WorldName"));
		double x = ConfigManager.getData().getDouble("Portals." + worldPath + "." + portalPath + ".Location.x");
		double y = ConfigManager.getData().getDouble("Portals." + worldPath + "." + portalPath + ".Location.y");
		double z = ConfigManager.getData().getDouble("Portals." + worldPath + "." + portalPath + ".Location.z");
		float yaw = (float) ConfigManager.getData().getDouble("Portals." + worldPath + "." + portalPath + ".Location.yaw");
		float pitch = (float) ConfigManager.getData().getDouble("Portals." + worldPath + "." + portalPath + ".Location.pitch");
		
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	public static void setPortalLocation(Location location, boolean spiritWorld, boolean northern) {
		
		if (portalExists(spiritWorld, northern)) {
			return;
		}
		
		String worldPath = spiritWorld ? "SpiritWorld" : "Overworld";
		String portalPath = northern ? "Northern" : "Southern";
		
		ConfigManager.getData().set("Portals." + worldPath + "." + portalPath + ".Valid", Boolean.valueOf(true));
		ConfigManager.getData().set(worldPath + ".WorldName", location.getWorld().getName());
		ConfigManager.getData().set("Portals." + worldPath + "." + portalPath + ".Location.x", location.getX());
		ConfigManager.getData().set("Portals." + worldPath + "." + portalPath + ".Location.y", location.getY());
		ConfigManager.getData().set("Portals." + worldPath + "." + portalPath + ".Location.z", location.getZ());
		ConfigManager.getData().set("Portals." + worldPath + "." + portalPath + ".Location.yaw", location.getYaw());
		ConfigManager.getData().set("Portals." + worldPath + "." + portalPath + ".Location.pitch", location.getPitch());
		
		ConfigManager.dataConfig.save();
	}
	
	public static boolean portalExists(boolean spiritWorld, boolean northern) {
		
		String worldPath = spiritWorld ? "SpiritWorld" : "Overworld";
		String portalPath = northern ? "Northern" : "Southern";
		
		return ConfigManager.getData().getBoolean("Portals." + worldPath + "." + portalPath + ".Valid");
	}
	
	public static void displayBar(Player player, boolean display) {
		
		if (display && !ProjectKorraSpirits.BAR.getPlayers().contains(player)) {
			ProjectKorraSpirits.BAR.addPlayer(player);
		}
		
		if (!display && ProjectKorraSpirits.BAR.getPlayers().contains(player)) {
			ProjectKorraSpirits.BAR.removePlayer(player);
		}
	}
}
