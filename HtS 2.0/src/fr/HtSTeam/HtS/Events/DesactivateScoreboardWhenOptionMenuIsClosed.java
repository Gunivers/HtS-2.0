package fr.HtSTeam.HtS.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class DesactivateScoreboardWhenOptionMenuIsClosed implements Listener {
	
	@EventHandler
	public void onOptionsClose(InventoryCloseEvent e) {
		if(e.getInventory().getTitle().equals("Options") || e.getInventory().getTitle().equals("Scoreboard")) {
			if (ScoreBoard.scoreboards.containsKey(e.getPlayer())) {
				ScoreBoard.scoreboards.get(e.getPlayer()).deactivate();
				ScoreBoard.scoreboards.remove(e.getPlayer());
			}
		}
	}
}
