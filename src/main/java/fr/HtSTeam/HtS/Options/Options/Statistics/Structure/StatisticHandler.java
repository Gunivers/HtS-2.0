package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Players.PlayerRemove;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.EntryBuilder;

public class StatisticHandler implements PlayerRemove {
	
	private static HashMap<UUID, HashMap<EnumStats, Object>> playerStats = new HashMap<UUID, HashMap<EnumStats, Object>>();
	
	{
		addToList();
	}
	
	@SuppressWarnings("serial")
	public static void init() {
		for (UUID uuid : PlayerInGame.playerInGame) {
			HashMap<EnumStats, Object> stats = new HashMap<EnumStats, Object>();
			for (EnumStats s : EnumStats.values()) {
				if (EnumStats.PLAYER_UUID == s)
					stats.put(s, new HashSet<String>() {{ add(uuid.toString()); }});
				else if (EnumStats.PLAYER_NAME == s)
					stats.put(s, new HashSet<String>() {{ add(PlayerInGame.uuidToName.get(uuid)); }});
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
	
	public static List<fr.HtSTeam.HtS.Scoreboard.Scoreboard.Entry> getDisplay(UUID uuid) {
		EntryBuilder builder = new EntryBuilder();
		for (Entry<EnumStats, Object> set : playerStats.get(uuid).entrySet())
			if (set.getKey().getDisplayName() != null)
				builder.next(set.getKey().getDisplayName() + " " + set.getValue());
		return builder.build();
	}
	
	public static void update(UUID uuid, EnumStats s) {
		if (!EnumState.getState().equals(EnumState.RUNNING) && !s.isTracked() && !playerStats.containsKey(uuid))
			return;
		HashMap<EnumStats, Object> stats = playerStats.get(uuid);
		stats.put(s, (int)stats.get(s) + 1);
		playerStats.put(uuid, stats);
	}
	
	public static void update(UUID uuid, EnumStats s, int value) {
		if (!s.isTracked() && !playerStats.containsKey(uuid))
			return;
		HashMap<EnumStats, Object> stats = playerStats.get(uuid);
		stats.put(s, (int)stats.get(s) + value);
		playerStats.put(uuid, stats);
	}
	
	@SuppressWarnings("unchecked")
	public static void update(UUID uuid, EnumStats s, String value) {
		if (!s.isTracked() && !playerStats.containsKey(uuid))
			return;
		HashMap<EnumStats, Object> stats = playerStats.get(uuid);
		HashSet<String> set = (HashSet<String>) stats.get(s);
		set.add(value);
		stats.put(s, set);
		playerStats.put(uuid, stats);
	}
	
	public static HashMap<UUID, HashMap<EnumStats, Object>> get() { return playerStats; }
	
	public static Object get(UUID uuid, EnumStats s) {
		if (playerStats.containsKey(uuid))
			return playerStats.get(uuid).get(s);
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
					if (stat.getDefaultValue() instanceof HashSet)
						values.add("\"" + String.join(",", (HashSet<String>) stats.get(stat)) + "\"");
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