package fr.HtSTeam.HtS;

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
import fr.HtSTeam.HtS.Player.DeathLoot;
import fr.HtSTeam.HtS.Utils.Nms;
import fr.HtSTeam.HtS.Utils.Files.FileExtractor;

public class Main extends JavaPlugin {
	
	public static World world;
	public static Main plugin;
	public static String HTSNAME = "HtS ";
	public static DeathLoot deathLoot = new DeathLoot();
	public static TimerTask timer;
	public static GameMode gamemode = new UHC();
		
	@Override
	public void onEnable() {
		plugin = this;
		System.out.println("Lancement de HtS...");
		
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
		
		timer = new TimerTask(0, 1);
		
		try { Nms.init(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
		
		new Command();
		Bukkit.getServer().getPluginManager().registerEvents(new Event(), plugin);
//		OptionRegister.register();
	}
	
	/*public static void run() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {	
			@Override
			public void run() {
			}
		}, 0L, 1L);
	}*/
}
