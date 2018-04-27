package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.Options.OptionRegister;

public class FallenKingdom implements GameMode {


	public FallenKingdom() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		CommandsFK cfk = new CommandsFK();
		
		Main.plugin.getCommand("base").setExecutor(cfk);
		pm.registerEvents(cfk, Main.plugin);
		OptionRegister.noRegen.setState(true);
		OptionRegister.goldenApple.setState(false);
	}
	
	@Override
	public void initialisation() {
		new EventManagerFK();
		for(BaseBuilder bb : BaseBuilder.baseList)
			bb.addAllPlayers();
	}

	@Override
	public String gamemodeToString() {
		return "Fallen Kingdom";
	}


}
