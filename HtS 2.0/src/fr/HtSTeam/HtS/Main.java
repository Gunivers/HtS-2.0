package fr.HtSTeam.HtS;

import org.bukkit.plugin.java.JavaPlugin;

import fr.HtSTeam.HtS.Commands.CommandsManager;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("Lancement de HtS...");
		CommandsManager.loadCommands(this);
	}
	
	public void test() {
		System.out.println("a");
	}
}
