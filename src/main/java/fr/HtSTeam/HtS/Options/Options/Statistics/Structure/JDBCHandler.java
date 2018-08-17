package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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
				
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(strInsert);
	}
	
	protected static void alter() throws SQLException {
		String strSelect = "SELECT * FROM `STATS_ALL_TIME` ORDER BY `PLAYER_UUID`";
		Statement stmt = conn.createStatement();
		ResultSetMetaData rs_meta = stmt.executeQuery(strSelect).getMetaData();

		strSelect = "SELECT * FROM `" + tableName + "` ORDER BY `PLAYER_UUID`";
		ResultSetMetaData hts_meta = stmt.executeQuery(strSelect).getMetaData();

		ArrayList<String> cols = new ArrayList<String>();
		for (int i = 1; i < hts_meta.getColumnCount() + 1; i++)
			cols.add(hts_meta.getColumnName(i));
		ArrayList<String> columns_alltime = new ArrayList<String>();
		for (int i = 1; i < rs_meta.getColumnCount() + 1; i++)
			columns_alltime.add(rs_meta.getColumnName(i));

		cols.removeIf(col -> columns_alltime.contains(col));

		if (!cols.isEmpty()) {
			String strAddCol = "ALTER TABLE `STATS_ALL_TIME` ADD COLUMN " + EnumStats.getSQLTableAddStats(cols);
			stmt.execute(strAddCol);
		}
	}

	protected static void update() throws SQLException {
		String strSelect = "SELECT * FROM `STATS_ALL_TIME` ORDER BY `PLAYER_UUID`";
		Statement stmt_rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt_rs.executeQuery(strSelect);		
		rs.beforeFirst();
		
		strSelect = "SELECT * FROM `" + tableName + "` ORDER BY `PLAYER_UUID`";
		Statement stmt_hts = conn.createStatement();
		ResultSet hts = stmt_hts.executeQuery(strSelect);	
		hts.beforeFirst();
		
		
		ArrayList<String> cols = new ArrayList<String>();
		for (int i = 1; i < hts.getMetaData().getColumnCount() + 1; i++)
			cols.add(hts.getMetaData().getColumnName(i));
		
		while (hts.next()) {
			if (rs.next() && rs.getString(2).equals(hts.getString(1))) {
				for (String col : cols) {
					if (EnumStats.valueOf(col).getDefaultValue() instanceof Number) {
						rs.updateInt(col, rs.getInt(col) + hts.getInt(col));
					} else if (EnumStats.valueOf(col).getDefaultValue() instanceof ArrayList) {
						HashSet<String> set = new HashSet<String>(Arrays.asList(rs.getString(col).split("\\s*,\\s*")));
						set.addAll(Arrays.asList(hts.getString(col).split("\\s*,\\s*")));
						rs.updateString(col, String.join(",", set));
					}
				}
				rs.updateRow();
			} else {
				rs.moveToInsertRow();
				for (String col : cols)
					if (EnumStats.valueOf(col).getDefaultValue() instanceof Number)
						rs.updateInt(col, hts.getInt(col));
					else if (EnumStats.valueOf(col).getDefaultValue() instanceof ArrayList)
						rs.updateString(col, hts.getString(col));
				rs.insertRow();
				rs.moveToCurrentRow();
			}
		}
		conn.close();
	}
}
