package fr.HtSTeam.HtS.Events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Player.PlayerDeath;
import fr.HtSTeam.HtS.Player.PlayerInGame;
import fr.HtSTeam.HtS.Player.PlayerReconnection;
import fr.HtSTeam.HtS.Players.Spectator.CustomChat;
import fr.HtSTeam.HtS.Players.Spectator.SeeInventory;
import fr.HtSTeam.HtS.Teams.TeamGive;

public class EventManager {
	
	public static void loadEvents(Main main) {

		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		pm.registerEvents(new PlayerDeath(), main);
		pm.registerEvents(new TeamGive(), main);
//		pm.registerEvents(new JoinLeaveEvent(), main);
		pm.registerEvents(new CloseOptionsEvent(), main);
		pm.registerEvents(new PlayerInGame(), main);
		pm.registerEvents(new WaitEvent(), main);
		pm.registerEvents(new CustomChat(), main);
		pm.registerEvents(new ReloadServerEvent(), main);
		pm.registerEvents(new SeeInventory(), main);
		pm.registerEvents(new PlayerReconnection(), main);
	}

}
