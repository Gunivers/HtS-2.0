package fr.HtSTeam.HtS;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Options.Structure.Timer;
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
		
		new FakeDeath();
		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
		EventManager.loadEvents(this);
		CommandsManager.loadCommands(this);
		OptionsRegister.register();
		ScoreboardLib.setPluginInstance(this);

		for(World w : Bukkit.getWorlds()) {if(w.getEnvironment() == Environment.NORMAL) { FileExtractor.wdir = w.getName() + "/data/loot_tables";}}
	}	
    
	public void executeTimer() {
		for (Class<?> c : getClasses(getFile(), "fr.HtSTeam.Options.Options")) {
			for (Method m : c.getMethods()) {
				try {
					if (m.isAnnotationPresent(Timer.class)) {
						for (Entry<OptionsManager, Object> entry : OptionsManager.optionsList.entrySet()) {
							if(entry.getValue().equals(c))
								if(entry.getKey().getValue() == "10")
									m.invoke(null);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
    private Set<Class<?>> getClasses(File jarFile, String packageName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		try {
			JarFile file = new JarFile(jarFile);
			for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
				JarEntry jarEntry = entry.nextElement();
				String name = jarEntry.getName().replace("/", ".");
				if (name.startsWith(packageName) && name.endsWith(".class"))
					classes.add(Class.forName(name.substring(0, name.length() - 6)));
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
}
