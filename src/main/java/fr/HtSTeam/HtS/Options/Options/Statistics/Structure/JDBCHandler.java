package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Utils.Files.PluginFile;

public class JDBCHandler {
	
	private static String url, db, pwd, tableName, comment;
	private static Connection conn;
	
	public JDBCHandler() {
		PluginFile f = new PluginFile(Main.plugin, "db_credentials.yml");
		url = (String) f.get("database.url");
		db = (String) f.get("database.name");
		pwd = (String) f.get("database.password");
		tableName = "STATS_" + Main.HTSNAME.replace(" ", "_");
	}
	
	protected static void createTable() throws SQLException {
		conn = DriverManager.getConnection(url, db, pwd);
		comment = "gamemode=" + Main.gamemode.gamemodeToString() + ",duration=" + Main.timer.getTimerInSeconds();		
		String strTable = "CREATE TABLE `" + db + "`.`" + tableName +  "` ( " + EnumStats.getSQLTableStatsTracked() +" ) comment='" + comment + "' ENGINE = InnoDB";
	
		Statement stmt = conn.createStatement();
		ResultSet rset = conn.getMetaData().getTables(null, null, tableName, null);
		if (rset.next())
			return;
		stmt.executeUpdate(strTable);
	}
	
	protected static void insert() throws SQLException {
		String strInsert = "INSERT INTO `" + tableName + "` (" + EnumStats.getSQLInsertStatsTracked() + ") VALUES " + StatisticHandler.getInsertValues();
		
		System.out.println(strInsert);
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(strInsert);
	}
	
	protected static void update() throws SQLException {
//		String strSelect = "SELECT * FROM `STATS_ALL_TIME`";	
//		Statement stmt = conn.createStatement();
//		ResultSet rset = stmt.executeQuery(strSelect);
//		rset.beforeFirst();
		conn.close();
	}
}
