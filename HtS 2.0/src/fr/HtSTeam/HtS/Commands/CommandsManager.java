package fr.HtSTeam.HtS.Commands;

import fr.HtSTeam.HtS.Main;

public class CommandsManager {
	
	public static void loadCommands(Main main) {
		main.getCommand("test").setExecutor(new Test(main));
		
	}

}
