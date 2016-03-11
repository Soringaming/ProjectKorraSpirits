package com.projectkorra.spirits;

import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.spirits.storage.DBConnection;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

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
}
