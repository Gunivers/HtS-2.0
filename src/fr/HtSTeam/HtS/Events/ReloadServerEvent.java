package fr.HtSTeam.HtS.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Scoreboard;

public class ReloadServerEvent implements Listener {

	
	@EventHandler
	public void onReload(PlayerCommandPreprocessEvent e) {
		String message = e.getMessage().replaceAll("/", "");
		if(message.equals("reload")) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.setPlayerListName(p.getName());
				p.setDisplayName(p.getName());
			}
			for(Scoreboard b : ScoreBoard.scoreboards.values())
			    b.deactivate();
		}
	}
	
}
