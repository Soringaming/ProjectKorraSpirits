package com.projectkorra.spirits;

import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.spirits.storage.DBConnection;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SpiritPlayer {

	private static final ConcurrentHashMap<UUID, SpiritPlayer> PLAYERS = new ConcurrentHashMap<UUID, SpiritPlayer>();

	private Player player;
	private UUID uuid;
	private String name;
	private SubElement spirit;

	public SpiritPlayer(UUID uuid, String name, SubElement spirit) {
		this.uuid = uuid;
		this.name = name;
		this.player = Bukkit.getPlayer(uuid);
		this.spirit = spirit;
		if (isSpirit()) {
			if (isLightSpirit()) {
				setLightSpirit();
			} else {
				setDarkSpirit();
			}
		}
		PLAYERS.put(uuid, this);
	}

	/**
	 * Gets the player of the {@link SpiritPlayer}.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Gets the name of the {@link SpiritPlayer}.
	 * 
	 * @return the player name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the unique identifier of the {@link SpiritPlayer}.
	 * 
	 * @return the uuid
	 */
	public UUID getUUID() {
		return this.uuid;
	}

	/**
	 * Convenience method to {@link #getUUID()} as a string.
	 * 
	 * @return string version of uuid
	 */
	public String getUUIDString() {
		return this.uuid.toString();
	}

	/**
	 * Checks to see if a player is a Spirit.
	 * 
	 * @return true if the player is a Spirit
	 */
	public boolean isSpirit() {
		return spirit != null;
	}

	/**
	 * Checks to see if a player is a Light Spirit.
	 * 
	 * @return true if player is a Light Spirit
	 */
	public boolean isLightSpirit() {
		if (!isSpirit()) return false;
		return spirit.equals(SpiritElement.LIGHT);
	}

	/**
	 * Sets a player as a Light Spirit.
	 */
	public void setLightSpirit() {
		if (!isLightSpirit()) {
			spirit = SpiritElement.LIGHT;
			DBConnection.sql.modifyQuery("UPDATE spirit_players SET spirit = 'l' WHERE uuid = '" + uuid + "'");
		}
	}

	/**
	 * Checks to see if a player is a Dark Spirit.
	 * 
	 * @return true if player is a Dark Spirit
	 */
	public boolean isDarkSpirit() {
		if (!isSpirit()) return false;
		return spirit.equals(SpiritElement.DARK);
	}

	/**
	 * Sets a player as a Dark Spirit.
	 */
	public void setDarkSpirit() {
		if (!isDarkSpirit()) {
			spirit = SpiritElement.DARK;
			DBConnection.sql.modifyQuery("UPDATE spirit_players SET spirit = 'd' WHERE uuid = '" + uuid + "'");
		}
	}
	
	/**
	 * Gets a players Spirit type.
	 * 
	 * @return spirit type.
	 */
	public SubElement getSpirit() {
		return spirit;
	}
	
	/**
	 * Remove a player's Spirit.
	 */
	public void removeSpirit() {
		DBConnection.sql.modifyQuery("UPDATE spirit_players SET spirit = NULL WHERE uuid = '" + uuid + "'");
	}
	
	public static SpiritPlayer getSpiritPlayer(OfflinePlayer oPlayer) {
		if (oPlayer == null) {
			return null;
		}
		return SpiritPlayer.getPlayers().get(oPlayer.getUniqueId());
	}
	
	public static SpiritPlayer getSpiritPlayer(Player player) {
		if (player == null) {
			return null;
		}
		return getSpiritPlayer(player.getName());
	}
	
	/**
	 * Attempts to get a {@link SpiritPlayer} from specified player name. this method tries to get
	 * a {@link Player} object and gets the uuid and then calls {@link #getSpiritPlayer(UUID)}
	 * 
	 * @param playerName The name of the Player
	 * @return The SpiritPlayer object if {@link SpiritPlayer#PLAYERS} contains the player name
	 * 
	 * @see #getSpiritPlayer(UUID)
	 */
	public static SpiritPlayer getSpiritPlayer(String playerName) {
		if (playerName == null) {
			return null;
		}
		Player player = Bukkit.getPlayer(playerName);
		OfflinePlayer oPlayer = player != null ? Bukkit.getOfflinePlayer(player.getUniqueId()) : null;
		return getSpiritPlayer(oPlayer);
	}
	
	/**
	 * Gets the map of {@link SpiritPlayer}s.
	 * 
	 * @return {@link #PLAYERS}
	 */
	public static ConcurrentHashMap<UUID, SpiritPlayer> getPlayers() {
		return PLAYERS;
	}
}
