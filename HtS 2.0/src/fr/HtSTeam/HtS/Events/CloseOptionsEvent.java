package fr.HtSTeam.HtS.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class CloseOptionsEvent implements Listener {
	
	@EventHandler
	public void onOptionsClose(InventoryCloseEvent e) {
		if(EnumState.getState().equals(EnumState.WAIT) && (e.getInventory().getTitle().equals("Options") || e.getInventory().getTitle().equals("Scoreboard"))) {
			if (ScoreBoard.scoreboards.containsKey(e.getPlayer())) {
				ScoreBoard.scoreboards.get(e.getPlayer()).deactivate();
				ScoreBoard.scoreboards.remove(e.getPlayer());
			}
		}
	}
}
