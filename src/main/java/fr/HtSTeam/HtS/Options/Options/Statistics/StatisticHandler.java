package fr.HtSTeam.HtS.Options.Options.Statistics;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Players.PlayerInGame;

public class StatisticHandler {
	
	private static HashMap<UUID, HashMap<EnumStats, Integer>> playerStats = new HashMap<UUID, HashMap<EnumStats, Integer>>();
	
	public static void init() {
		for (UUID uuid : PlayerInGame.playerInGame) {
			HashMap<EnumStats, Integer> stats = new HashMap<EnumStats, Integer>();
			for (EnumStats s : EnumStats.values())
				if (s.isTracked())
					stats.put(s, 0);
			playerStats.put(uuid, stats);
		}
					
	}
	
	public static void update(Player p, EnumStats s) {
		if (!EnumState.getState().equals(EnumState.RUNNING) && !s.isTracked())
			return;
		HashMap<EnumStats, Integer> stats = playerStats.get(p.getUniqueId());
		stats.put(s, stats.get(s) + 1);
		playerStats.put(p.getUniqueId(), stats);
	}

	public static void updateTrackedStats() {
		playerStats.forEach((uuid, stats) -> { stats.forEach((stat, value) -> { if (!stat.isTracked()) stats.remove(stat); }); });
	}
}