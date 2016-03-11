package com.projectkorra.spirits.storage;

import com.projectkorra.spirits.ProjectKorraSpirits;

public class DBConnection {

	public static Database sql;

	public static String host;
	public static int port;
	public static String db;
	public static String user;
	public static String pass;
	public static boolean isOpen = false;

	public static void init() {
		if (ProjectKorraSpirits.plugin.getConfig().getString("Storage.engine").equalsIgnoreCase("mysql")) {
			sql = new MySQL(ProjectKorraSpirits.log, "Establishing MySQL Connection...", host, port, user, pass, db);
			if (((MySQL) sql).open() == null) {
				ProjectKorraSpirits.log.severe("Disabling due to database error");
				return;
			}

			isOpen = true;
			ProjectKorraSpirits.log.info("Database connection established.");

			if (!sql.tableExists("spirit_players")) {
				ProjectKorraSpirits.log.info("Creating spirit_players table");
				String query = "CREATE TABLE `spirit_players` (" 
				+ "`uuid` varchar(36) NOT NULL," 
						+ "`player` varchar(16) NOT NULL," 
						+ "`spirit` varchar(1)," 
						+ " PRIMARY KEY (uuid));";
				sql.modifyQuery(query);
			}

		} else {
			sql = new SQLite(ProjectKorraSpirits.log, "Establishing SQLite Connection.", "spirits.db", ProjectKorraSpirits.plugin.getDataFolder().getAbsolutePath());
			if (((SQLite) sql).open() == null) {
				ProjectKorraSpirits.log.severe("Disabling due to database error");
				return;
			}

			isOpen = true;
			if (!sql.tableExists("spirit_players")) {
				ProjectKorraSpirits.log.info("Creating spirit_players table.");
				String query = "CREATE TABLE `spirit_players` (" 
				+ "`uuid` TEXT(36) PRIMARY KEY," 
						+ "`player` TEXT(16)," 
						+ "`spirit` TEXT(1));";
				sql.modifyQuery(query);
			}
		}
	}

	public static boolean isOpen() {
		return isOpen;
	}
}
