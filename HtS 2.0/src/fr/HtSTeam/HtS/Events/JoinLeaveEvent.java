package fr.HtSTeam.HtS.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class JoinLeaveEvent implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (EnumState.getState() == EnumState.RUNNING) {
			ScoreBoard.scoreboards.get(p.getUniqueId()).activate();
		}
	}
	
}
