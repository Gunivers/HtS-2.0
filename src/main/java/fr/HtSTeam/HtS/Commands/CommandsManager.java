package fr.HtSTeam.HtS.Commands;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.CommandsFK;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.CommandSyT;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Teams.TeamCommand;

public class CommandsManager {
	
	public static PlayStopCommands playStop = new PlayStopCommands();
	public static CommandSyT csyt = new CommandSyT();
	public static StartCommand sc = new StartCommand();
	
	public static void loadCommands(Main main) {
		main.getCommand("option").setExecutor(new OptionCommand());
		main.getCommand("start").setExecutor(sc);
		main.getCommand("run").setExecutor(sc);
		main.getCommand("team").setExecutor(new TeamCommand());
		main.getCommand("play").setExecutor(playStop);
		main.getCommand("pause").setExecutor(playStop);
		main.getCommand("end").setExecutor(playStop);
		UtilCommands uc = new UtilCommands();
		main.getCommand("heal").setExecutor(uc);
		main.getCommand("feed").setExecutor(uc);
		main.getCommand("radar").setExecutor(csyt);
		main.getCommand("target").setExecutor(csyt);
		main.getCommand("base").setExecutor(new CommandsFK());
		main.getCommand("load").setExecutor(OptionRegister.loadPreset);
		main.getCommand("remove").setExecutor(new RemoveCommand(main));
	}
}
