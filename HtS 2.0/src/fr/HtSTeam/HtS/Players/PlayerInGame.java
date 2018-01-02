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

public class PlayerInGame implements Listener {

	public static List<UUID> playerInGame = new ArrayList<UUID>();
	
	public List<UUID> getPlayerInGame() { return playerInGame; }
	
	public void removePlayer(Player p) { playerInGame.remove(p.getUniqueId()); }
	
	public void addPlayer(Player p) {
		if(!playerInGame.contains(p.getUniqueId()))
			playerInGame.add(p.getUniqueId());
	}
	
	public void clear() { playerInGame.clear(); }
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		System.out.println(!playerInGame.contains(e.getPlayer().getUniqueId()));
		System.out.println(e.getPlayer().getUniqueId());
		System.out.println(playerInGame.get(0));
	    if(!EnumState.getState().equals(EnumState.WAIT)) {
			if(!playerInGame.contains(e.getPlayer().getUniqueId()))
				e.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
	}
}
