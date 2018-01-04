package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.Options.OptionsRegister;

public class FallenKingdom implements GameMode {


	public FallenKingdom() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		CommandsFK cfk = new CommandsFK();
		
		Main.plugin.getCommand("base").setExecutor(cfk);
		pm.registerEvents(cfk, Main.plugin);
		OptionsRegister.noRegen.setState(true);
	}
	
	@Override
	public void initialisation() {
		new EventManagerFK();
	}

	@Override
	public String gamemodeTotring() {
		return "Fallen Kingdom";
	}


}
