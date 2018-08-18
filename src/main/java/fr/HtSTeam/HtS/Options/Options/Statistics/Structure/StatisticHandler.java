package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Players.PlayerManager;
import fr.HtSTeam.HtS.Players.PlayerRemove;

public class StatisticHandler implements PlayerRemove{
	
	private static HashMap<UUID, HashMap<EnumStats, Object>> playerStats = new HashMap<UUID, HashMap<EnumStats, Object>>();
	
	@SuppressWarnings("serial")
	public static void init() {
		for (UUID uuid : PlayerInGame.playerInGame) {
			HashMap<EnumStats, Object> stats = new HashMap<EnumStats, Object>();
			for (EnumStats s : EnumStats.values()) {
				if (EnumStats.PLAYER_UUID == s)
					stats.put(s, new ArrayList<String>() {{ add(uuid.toString()); }});
				else if (EnumStats.PLAYER_NAME == s)
					stats.put(s, new ArrayList<String>() {{ add(PlayerInGame.uuidToName.get(uuid)); }});
				else if (s.isTracked())
					stats.put(s, s.getDefaultValue());
			}
			playerStats.put(uuid, stats);
		}
	}
	
	@Override
	public void removePlayer(UUID uuid, String name) {
		playerStats.remove(uuid);		
	}
	
	public static void save() throws SQLException {
		JDBCHandler.createTable();
		JDBCHandler.insert();
		JDBCHandler.alter();
		JDBCHandler.update();
	}
	
	public static void display() {
		playerStats.forEach((uuid, stats) -> { stats.forEach((stat, value) -> { if(PlayerManager.isConnected(uuid)) Bukkit.getPlayer(uuid).sendMessage(stat.toString() + ":   " + value); }); });
	}
	
	public static void update(Player p, EnumStats s) {
		if (!EnumState.getState().equals(EnumState.RUNNING) && !s.isTracked() && !playerStats.containsKey(p.getUniqueId()))
			return;
		HashMap<EnumStats, Object> stats = playerStats.get(p.getUniqueId());
		stats.put(s, (int)stats.get(s) + 1);
		playerStats.put(p.getUniqueId(), stats);
	}
	
	public static void update(Player p, EnumStats s, int value) {
		if (!s.isTracked() && !playerStats.containsKey(p.getUniqueId()))
			return;
		HashMap<EnumStats, Object> stats = playerStats.get(p.getUniqueId());
		stats.put(s, (int)stats.get(s) + value);
		playerStats.put(p.getUniqueId(), stats);
	}
	
	public static HashMap<UUID, HashMap<EnumStats, Object>> get() { return playerStats; }
	
	public static Object get(Player p, EnumStats s) {
		if (playerStats.containsKey(p.getUniqueId()))
			return playerStats.get(p.getUniqueId()).get(s);
		return null;
	}

	@SuppressWarnings("unchecked")
	protected static String getInsertValues() {
		ArrayList<String> rows = new ArrayList<String>();
		playerStats.forEach((uuid, stats) -> {
			ArrayList<String> values = new ArrayList<String>();
			for (int i = 0; i < EnumStats.values().length; i++) {
				EnumStats stat = EnumStats.values()[i];
				if (stat.isTracked())
					if (stat.getDefaultValue() instanceof ArrayList)
						values.add("\"" + String.join(",", (ArrayList<String>) stats.get(stat)) + "\"");
					else
						values.add("\"" + stats.get(stat).toString() + "\"");
			}
			rows.add("(" + String.join(",", values) + ")");
		});
		if(rows.isEmpty())
			return null;
		return String.join(",", rows);
	}
}