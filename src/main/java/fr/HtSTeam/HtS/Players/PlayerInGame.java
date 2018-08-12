package fr.HtSTeam.HtS.Players;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Teams.TeamBuilder;

public class PlayerInGame implements Listener {

	public static List<UUID> playerInGame = new ArrayList<UUID>();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
	    if(!EnumState.getState().equals(EnumState.WAIT)) {
			if(!playerInGame.contains(e.getPlayer().getUniqueId()))
				e.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
	}
	
	public static void removeFromGame(Player p) {
		removeFromGame(p.getUniqueId());
	}
	
	public static void removeFromGame(UUID uuid) {
		playerInGame.remove(uuid);
		if (TeamBuilder.playerTeam.get(uuid) != null)
			TeamBuilder.playerTeam.get(uuid).removePlayer(uuid);
	}
}
