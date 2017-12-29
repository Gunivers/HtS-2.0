package fr.HtSTeam.HtS.Commands;

import fr.HtSTeam.HtS.Main;

public class CommandsManager {
	
	public static void loadCommands(Main main) {
		main.getCommand("option").setExecutor(new OptionCommand(main));
		main.getCommand("start").setExecutor(new StartCommand(main));
		
	}

}
