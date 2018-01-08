package fr.HtSTeam.HtS.Events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;
import fr.HtSTeam.HtS.Teams.TeamBuilder;

public class JoinLeaveEvent implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (EnumState.getState() == EnumState.RUNNING) {
			ScoreBoard.send(p);
			if (TeamBuilder.teamList.size() != 0 && p.getGameMode() != GameMode.SPECTATOR)
				p.setDisplayName(ChatColor.valueOf(TeamBuilder.playerTeam.get(p.getUniqueId()).getTeamColor().toUpperCase()) + p.getName());
		}	
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if(EnumState.getState() == EnumState.RUNNING)
			ScoreBoard.scoreboards.remove(e.getPlayer().getUniqueId());
	}
}
