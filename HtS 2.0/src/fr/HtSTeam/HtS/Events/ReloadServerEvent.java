package fr.HtSTeam.HtS.Events;

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
			for(Scoreboard b : ScoreBoard.scoreboards.values())
			    b.deactivate();
		}
	}
	
}
