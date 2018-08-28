package fr.HtSTeam.HtS;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.plugin.java.JavaPlugin;

import fr.HtSTeam.HtS.Commands.CommandsManager;
import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.JDBCHandler;
import fr.HtSTeam.HtS.Options.Structure.TimerTask;
import fr.HtSTeam.HtS.Players.DeathLoot;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardLib;
import fr.HtSTeam.HtS.Teams.TeamRegister;
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
				
		ScoreboardLib.setPluginInstance(plugin);
		OptionRegister.register();
		CommandsManager.loadCommands(plugin);
		new TeamRegister();
		new JDBCHandler();
		
		try {
			HTSNAME += JDBCHandler.getHtsNumber();
			ScoreBoard.sb_name = HTSNAME;
		} catch (SQLException e) { e.printStackTrace(); }		
	}
	
	/*public static void run() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {	
			@Override
			public void run() {
				System.out.println("=======================================");
				Bukkit.getOnlinePlayers().forEach(p -> { System.out.println(p.getName() + "    " + p.getStatistic(Statistic.PLAYER_KILLS)); });
				System.out.println("=======================================");
			}
		}, 0L, 1L);
	}*/
}
