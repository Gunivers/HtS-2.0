package fr.HtSTeam.HtS.Events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Players.Spectator.CustomChat;
import fr.HtSTeam.HtS.Teams.TeamGive;

public class EventManager {
	
	public static void loadEvents(Main main) {

		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		pm.registerEvents(new TeamGive(), main);
		pm.registerEvents(new JoinLeaveEvent(), main);
		pm.registerEvents(new CloseOptionsEvent(), main);
		pm.registerEvents(new PlayerInGame(), main);
		pm.registerEvents(new WaitEvent(), main);
		pm.registerEvents(new CustomChat(), main);
	}

}
