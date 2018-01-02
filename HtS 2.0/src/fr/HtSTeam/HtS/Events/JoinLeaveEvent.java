package fr.HtSTeam.HtS.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;
import fr.HtSTeam.HtS.Teams.TeamManager;

public class JoinLeaveEvent implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (EnumState.getState() == EnumState.RUNNING) {
			p.setScoreboard(TeamManager.playerTeam.get(p).getScoreboard());
			ScoreBoard.send(p);
		}
	}
	
}
