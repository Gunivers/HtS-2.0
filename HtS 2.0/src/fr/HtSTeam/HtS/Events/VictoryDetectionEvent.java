package fr.HtSTeam.HtS.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.Structure.TeamManager;
import fr.HtSTeam.HtS.Utils.JSON;

public class VictoryDetectionEvent implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(TeamManager.teamList.size() == 1) {
			EnumState.setState(EnumState.FINISHING);
			JSON.sendAll(TeamManager.teamList.get(0).getTeamColor() + "La team" + TeamManager.teamList.get(0).getTeamName() + " a gagné !", null, 5);
		} else if(Main.playerInGame.getPlayerInGame().size() == 1) {
			EnumState.setState(EnumState.FINISHING);
			JSON.sendAll(Main.playerInGame.getPlayerInGame().get(0).getName() + " §2 a gagné !", null, 5);
		}
	}
	
}
