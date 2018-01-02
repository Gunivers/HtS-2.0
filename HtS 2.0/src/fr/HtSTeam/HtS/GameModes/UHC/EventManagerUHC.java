package fr.HtSTeam.HtS.GameModes.UHC;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;

public class EventManagerUHC {
	
	public EventManagerUHC() {
		
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new FakeDeath(), Main.plugin);
		pm.registerEvents(new VictoryDetectionEvent(), Main.plugin);
	}

}
