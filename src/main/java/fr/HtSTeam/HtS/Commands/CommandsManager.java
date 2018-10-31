package fr.HtSTeam.HtS.Commands;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.CommandsFK;
import fr.HtSTeam.HtS.Options.OptionRegister;

public class CommandsManager {
	
	public static PlayStopCommands playStop = new PlayStopCommands();
	public static StartCommand sc = new StartCommand();
	
	public static void loadCommands(Main main) {
		main.getCommand("option").setExecutor(new OptionCommand());
		main.getCommand("start").setExecutor(sc);
		main.getCommand("run").setExecutor(sc);
		main.getCommand("play").setExecutor(playStop);
		main.getCommand("pause").setExecutor(playStop);
		main.getCommand("end").setExecutor(playStop);
		UtilCommands uc = new UtilCommands();
		main.getCommand("heal").setExecutor(uc);
		main.getCommand("feed").setExecutor(uc);
		main.getCommand("base").setExecutor(new CommandsFK());
		main.getCommand("load").setExecutor(OptionRegister.loadPreset);
		main.getCommand("remove").setExecutor(new RemoveCommand(main));
	}
}
