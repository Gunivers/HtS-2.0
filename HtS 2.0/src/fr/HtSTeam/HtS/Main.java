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

public class Main extends JavaPlugin {
	
	public HashMap<Player, UUID> uuidPlayer = new HashMap<>();

	@Override
	public void onEnable() {
		System.out.println("Lancement de HtS...");
		
		for(World world : Bukkit.getWorlds()) {
			world.setDifficulty(Difficulty.HARD);
			if(world.getEnvironment() == Environment.NORMAL) {
				world.setPVP(false);
				world.setSpawnLocation(0, 205, 0);
			}	
		}
		
		CommandsManager.loadCommands(this);
	}
	
	public void test() {
		System.out.println("a");
	}
}
