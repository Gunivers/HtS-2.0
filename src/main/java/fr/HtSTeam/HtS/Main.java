package fr.HtSTeam.HtS;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.plugin.java.JavaPlugin;

import fr.HtSTeam.HtS.Commands.Structure.Command;
import fr.HtSTeam.HtS.Events.Structure.Event;
import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.GameModes.TimerTask;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.Utils.Logger;
import fr.HtSTeam.HtS.Utils.Nms;
import fr.HtSTeam.HtS.Utils.Files.FileExtractor;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public static String HTSNAME = "HtS ";
	public static Logger LOGGER;
	
	public static World world;
	public static TimerTask timer;
	public static GameMode gamemode = new UHC();
		
	@Override
	public void onEnable() {
		plugin = this;
		LOGGER = new Logger(new File(plugin.getDataFolder() + "/logs/latest.log"));
		
		LOGGER.logInfo("Starting HtS...");
		
		try { Nms.init(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
		
		new Command();
		Bukkit.getServer().getPluginManager().registerEvents(new Event(), plugin);
//		OptionRegister.register();
		
		timer = TimerTask.getInstance(0, 1);
		initWorlds();
		
		LOGGER.logInfo("HtS running!");
	}
	
	private void initWorlds() {
		for(World world : Bukkit.getWorlds()) {
			world.setDifficulty(Difficulty.HARD);
			if(world.getEnvironment() == Environment.NORMAL) {
				Main.world = world; 
				FileExtractor.wdir = world.getName() + "/data/loot_tables";
				world.setPVP(false);
				world.setSpawnLocation(0, 205, 0);
				world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
			}	
		}
	}
	
	@Override
	public void onDisable() {
		LOGGER.logInfo("Shutting down HtS...");
		LOGGER.logInfo("HtS shut down!");
		LOGGER.close();
	}
	
	/*public static void run() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {	
			@Override
			public void run() {
			}
		}, 0L, 1L);
	}*/
}
