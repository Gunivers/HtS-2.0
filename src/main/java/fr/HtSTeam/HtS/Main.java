package fr.HtSTeam.HtS;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.plugin.java.JavaPlugin;

import fr.HtSTeam.HtS.Commands.Structure.Command;
import fr.HtSTeam.HtS.Events.Structure.Event;
import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.Options.Structure.TimerTask;
import fr.HtSTeam.HtS.Utils.Logger;
import fr.HtSTeam.HtS.Utils.Nms;
import fr.HtSTeam.HtS.Utils.Files.FileExtractor;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public static String HTSNAME = "HtS ";
	public static Logger logger;
	
	public static World world;
	public static TimerTask timer;
	public static GameMode gamemode = new UHC();
		
	@Override
	public void onEnable() {
		plugin = this;
		logger = new Logger(new File("log.txt"), this);
		
		System.out.println("[HtS] Starting HtS...");
		
		try { Nms.init(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
		
		new Command();
		Bukkit.getServer().getPluginManager().registerEvents(new Event(), plugin);
//		OptionRegister.register();
		
		timer = new TimerTask(0, 1);
		initWorlds();
		
		System.out.println("[HtS] HtS running!");
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
	
	/*public static void run() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {	
			@Override
			public void run() {
			}
		}, 0L, 1L);
	}*/
}
