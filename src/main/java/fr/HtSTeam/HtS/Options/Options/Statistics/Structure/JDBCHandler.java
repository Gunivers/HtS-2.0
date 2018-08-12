package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
		
		System.out.println(strInsert);
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(strInsert);
	}
	
	protected static void update() throws SQLException {
		String strSelect = "SELECT * FROM `STATS_ALL_TIME` ORDER BY `PLAYER_UUID`";	
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(strSelect);
		rs.beforeFirst();
		ResultSetMetaData rs_meta = rs.getMetaData();
		
		strSelect = "SELECT * FROM `" + tableName + "` ORDER BY `PLAYER_UUID`";
		ResultSet hts = stmt.executeQuery(strSelect);
		hts.beforeFirst();
		ResultSetMetaData hts_meta = hts.getMetaData();
		
		ArrayList<String> cols = new ArrayList<String>();		
		for (int i = 0; i < hts_meta.getColumnCount() + 1; i++)
			cols.add(hts_meta.getColumnName(i));
		ArrayList<String> columns = cols;
		cols.forEach(col -> {
			try {
				for (int i = 0; i < rs_meta.getColumnCount(); i++)
					if (rs_meta.getColumnName(i).equals(col))
						cols.remove(col);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
			
		String strAddCol = "ALTER TABLE `STATS_ALL_TIME` ADD COLUMN " + EnumStats.getSQLTableAddStats(cols);
		stmt.execute(strAddCol);
		
		strSelect = "SELECT * FROM `STATS_ALL_TIME` ORDER BY `PLAYER_UUID`";
		rs = stmt.executeQuery(strSelect);
		rs.beforeFirst();
		
		while (hts.next()) {
			rs.next();
			if (rs.getString(1).equals(hts.getString(1))) {
				for (String col : columns) {
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
				for (String col : columns) {
					if (EnumStats.valueOf(col).getDefaultValue() instanceof Number) {
						rs.updateInt(col, hts.getInt(col));
					} else if (EnumStats.valueOf(col).getDefaultValue() instanceof ArrayList) {
						rs.updateString(col, hts.getString(col));
					}
				}
				rs.insertRow();
				rs.moveToCurrentRow();
			}
		}

		conn.close();
	}
}
