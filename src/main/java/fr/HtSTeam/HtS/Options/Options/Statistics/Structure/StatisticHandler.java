package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.Equator;
import org.apache.commons.collections4.IterableUtils;
import org.bukkit.Bukkit;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Players.PlayerManager;
import fr.HtSTeam.HtS.Players.PlayerRemove;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Entry;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.EntryBuilder;

public class StatisticHandler implements PlayerRemove {
	
	private static HashMap<UUID, HashMap<EnumStats, Object>> playerStats = new HashMap<UUID, HashMap<EnumStats, Object>>();
	private static HashMap<EnumStats, ArrayList<String>> mvpStats = new HashMap<EnumStats, ArrayList<String>>();
	private static ArrayList<EnumStats> stats = new ArrayList<EnumStats>();
	private static HashMap<UUID, Integer> itrs = new HashMap<UUID, Integer>();
	
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
	
	@SuppressWarnings("unchecked")
	public static void display() {
		playerStats.forEach((uuid, stats) -> {
			if (!PlayerManager.isConnected(uuid))
				return;
			Bukkit.getPlayer(uuid).sendMessage("===== Vos Statistiques =====");
			stats.forEach((stat, value) -> {
				if (stat.getDisplayName() != null && value != null && value instanceof Number)
					Bukkit.getPlayer(uuid).sendMessage(stat.getDisplayName() + " : " + value);
				else if (stat.getDisplayName() != null  && value instanceof HashSet && !((HashSet<String>) value).isEmpty())
					Bukkit.getPlayer(uuid).sendMessage(stat.getDisplayName() + " : " + String.join(", ", (HashSet<String>) value));
			});
		});
		mvps();
		ScoreBoard.sb_name = "MVP";
		ScoreBoard.refresh_rate = 20L * 5L;
		Bukkit.getOnlinePlayers().forEach(player -> { itrs.put(player.getUniqueId(), 0); ScoreBoard.send(player); });
	}
	
	private static void mvps() {
		for (int i = 0; i < EnumStats.values().length; i++) {
			EnumStats stat = EnumStats.values()[i];
			if (!stat.isTracked() || stat.getDisplayName() == null || !(stat.getDefaultValue() instanceof Number))
				continue;
			stats.add(stat);
			ArrayList<Integer> values = new ArrayList<Integer>();
			playerStats.forEach((uuid, stats) -> { values.add((int) stats.get(stat)); });
			Collections.sort(values, Collections.reverseOrder());
			ArrayList<String> mvps = new ArrayList<String>();
			int size = (values.size() > 5) ? 5 : values.size();
			for (int j = 0; j < size; j++) {
				final int final_j = j;
				playerStats.forEach((uuid, stats) -> { if (values.get(final_j) == stats.get(stat) && !IterableUtils.contains(mvps, PlayerInGame.uuidToName.get(uuid), new Equator<String>() {
					@Override
					public boolean equate(String o1, String o2) {
						return o2.contains(o1);
					}

					@Override
					public int hash(String o) {
						return o.hashCode();
					}
				})) mvps.add(PlayerInGame.uuidToName.get(uuid) + " - " + stats.get(stat)); }); }
			mvpStats.put(stat, mvps);
		}
	}
	
	public static List<Entry> getDisplayStatMvp(UUID uuid) {
		EntryBuilder entry = new EntryBuilder();
		int itr = itrs.get(uuid);
		entry.next(stats.get(itr).getDisplayName()).blank();
		mvpStats.get(stats.get(itr)).forEach(str -> { entry.next(str); });
		itr++;
		itrs.put(uuid, itr);
		if (itrs.get(uuid) == mvpStats.size())
			itrs.put(uuid, 0);
		return entry.build();
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