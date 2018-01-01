package fr.HtSTeam.HtS;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import fr.HtSTeam.HtS.Commands.CommandsManager;
import fr.HtSTeam.HtS.Events.EventManager;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Players.DeathLoot;
import fr.HtSTeam.HtS.Players.FakeDeath;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardLib;
import fr.HtSTeam.HtS.Utils.FileExtractor;

public class Main extends JavaPlugin {
	
	public HashMap<Player, UUID> uuidPlayer = new HashMap<>();
	public static Main plugin;
	public final static String HTSNAME = "HtS XII";
	public static PlayerInGame playerInGame = new PlayerInGame();
	public static DeathLoot deathLoot = new DeathLoot();
	public static fr.HtSTeam.HtS.Utils.TimerTask timer;
	
	public static Scoreboard b;
	
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

		b = Bukkit.getScoreboardManager().getNewScoreboard();
		
		timer = new fr.HtSTeam.HtS.Utils.TimerTask(0, 1);
		
		for(World w : Bukkit.getWorlds()) {if(w.getEnvironment() == Environment.NORMAL) { FileExtractor.wdir = w.getName() + "/data/loot_tables";}}
		
		new FakeDeath();
		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
		EventManager.loadEvents(this);
		CommandsManager.loadCommands(this);
		OptionsRegister.register();
		ScoreboardLib.setPluginInstance(this);
	}
}
