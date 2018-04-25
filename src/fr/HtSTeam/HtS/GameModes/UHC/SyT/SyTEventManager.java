package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;

public class SyTEventManager {

	public SyTEventManager() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new SyTDeathEvent(), Main.plugin);
	}

}
