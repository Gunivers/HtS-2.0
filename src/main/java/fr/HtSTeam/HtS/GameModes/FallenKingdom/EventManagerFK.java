package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;

public class EventManagerFK {

	
	public EventManagerFK() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		pm.registerEvents(new ChargedCreeper(), Main.plugin);
		pm.registerEvents(new BasesEvent(), Main.plugin);
		pm.registerEvents(new CakeFirstGame(), Main.plugin);
		
	}
	
}
