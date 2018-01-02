package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.GameMode;

public class FallenKingdom implements GameMode {


	public FallenKingdom() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		CommandsFK cfk = new CommandsFK();
		
		Main.plugin.getCommand("setbase").setExecutor(cfk);
		pm.registerEvents(cfk, Main.plugin);
	}
	
	@Override
	public void initialisation() {
		new EventManagerFK();
	}


}
