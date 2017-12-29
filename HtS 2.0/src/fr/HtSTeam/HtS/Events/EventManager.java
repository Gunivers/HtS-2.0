package fr.HtSTeam.HtS.Events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.PlayerInGame;

public class EventManager {
	
	public static void loadEvents(Main main) {

		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		pm.registerEvents(new JoinEvent(), main);
		pm.registerEvents(new PlayerInGame(), main);
		pm.registerEvents(new WaitEvent(), main);
	}

}
