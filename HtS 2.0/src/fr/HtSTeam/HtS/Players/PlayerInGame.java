package fr.HtSTeam.HtS.Players;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.HtSTeam.HtS.EnumState;

public class PlayerInGame implements Listener {

	public static List<UUID> playerInGame = new ArrayList<UUID>();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
	    if(!EnumState.getState().equals(EnumState.WAIT)) {
			if(!playerInGame.contains(e.getPlayer().getUniqueId()))
				e.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
	}
}
