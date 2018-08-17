package fr.HtSTeam.HtS.Players;

import java.util.UUID;

import org.bukkit.Bukkit;

public class PlayerManager {
	
	public static boolean isConnected(UUID uuid) {
		return Bukkit.getPlayer(uuid) != null;
	}
	
}
