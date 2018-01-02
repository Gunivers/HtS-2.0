package fr.HtSTeam.HtS.Commands;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Teams.TeamCommand;

public class CommandsManager {
	
	public static void loadCommands(Main main) {
		main.getCommand("option").setExecutor(new OptionCommand(main));
		main.getCommand("start").setExecutor(new StartCommand(main));
		main.getCommand("run").setExecutor(new StartCommand(main));
		main.getCommand("team").setExecutor(new TeamCommand());
		main.getCommand("test").setExecutor(new OptionCommand(main));
		PlayStopCommands playStop = new PlayStopCommands();
		main.getCommand("play").setExecutor(playStop);
		main.getCommand("pause").setExecutor(playStop);
		main.getCommand("end").setExecutor(playStop);
	}

}
