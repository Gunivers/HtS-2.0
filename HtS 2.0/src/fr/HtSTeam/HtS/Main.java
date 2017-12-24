package fr.HtSTeam.HtS;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.HtSTeam.HtS.Commands.CommandsManager;
import fr.HtSTeam.HtS.Options.OptionsManager;
import fr.HtSTeam.HtS.Options.Options.BorderOption;

public class Main extends JavaPlugin {
	
	public HashMap<Player, UUID> uuidPlayer = new HashMap<>();
	public static Main plugin;

	@Override
	public void onEnable() {
		plugin = this;
		System.out.println("Lancement de HtS...");
		
		for(World world : Bukkit.getWorlds()) {
			world.setDifficulty(Difficulty.HARD);
			if(world.getEnvironment() == Environment.NORMAL) {
				world.setPVP(false);
				world.setSpawnLocation(0, 205, 0);
			}	
		}
		
		CommandsManager.loadCommands(this);
		new BorderOption();
		System.out.println(OptionsManager.optionsList.size());
		
	}
	
}
