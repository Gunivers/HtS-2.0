package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Players.PlayerInGame;

public class StatisticHandler {
	
	private static HashMap<UUID, HashMap<EnumStats, Object>> playerStats = new HashMap<UUID, HashMap<EnumStats, Object>>();
	
	public static void init() {
		for (UUID uuid : PlayerInGame.playerInGame) {
			HashMap<EnumStats, Object> stats = new HashMap<EnumStats, Object>();
			for (EnumStats s : EnumStats.values())
				if (s.isTracked())
					stats.put(s, s.getDefaultValue());
			playerStats.put(uuid, stats);
		}	
	}
	
	public static void display() {
		playerStats.forEach((uuid, stats) -> { stats.forEach((stat, value) -> { Bukkit.getPlayer(uuid).sendMessage(stat.toString() + ":   " + value); }); });
	}
	
	public static void update(Player p, EnumStats s) {
		if (!EnumState.getState().equals(EnumState.RUNNING) && !s.isTracked() && !playerStats.containsKey(p.getUniqueId()))
			return;
		HashMap<EnumStats, Object> stats = playerStats.get(p.getUniqueId());
		stats.put(s, (int)stats.get(s) + 1);
		playerStats.put(p.getUniqueId(), stats);
	}
	
	public static void update(Player p, EnumStats s, int value) {
		if (!EnumState.getState().equals(EnumState.RUNNING) && !s.isTracked() && !playerStats.containsKey(p.getUniqueId()))
			return;
		HashMap<EnumStats, Object> stats = playerStats.get(p.getUniqueId());
		stats.put(s, (int)stats.get(s) + value);
		playerStats.put(p.getUniqueId(), stats);
	}
	
	public static Object get(Player p, EnumStats s) {
		if (playerStats.containsKey(p.getUniqueId()))
			return playerStats.get(p.getUniqueId()).get(s);
		return null;
	}
}