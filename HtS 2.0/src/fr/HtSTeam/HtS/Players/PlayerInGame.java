package fr.HtSTeam.HtS.Players;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.HtSTeam.HtS.EnumState;

public class PlayerInGame implements Listener {

	private List<Player> playerInGame = new ArrayList<Player>();
	
	public List<Player> getPlayerInGame() { return playerInGame; }
	
	public void removePlayer(Player p) { playerInGame.remove(p); }
	
	public void addPlayer(Player p) {
		if(!playerInGame.contains(p))
			playerInGame.add(p);
	}
	
	public void clear() { playerInGame.clear(); }
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(EnumState.getState().equals(EnumState.WAIT) && !playerInGame.contains(e.getPlayer()))
			playerInGame.add(e.getPlayer());
		else if(!EnumState.getState().equals(EnumState.WAIT)) {
			if(!playerInGame.contains(e.getPlayer()))
				e.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
	}
}
