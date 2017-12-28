package fr.HtSTeam.HtS;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import fr.HtSTeam.HtS.Commands.CommandsManager;
import fr.HtSTeam.HtS.Events.EventManager;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.Timer;
import fr.HtSTeam.HtS.Options.Structure.UsingTimer;
import fr.HtSTeam.HtS.Scoreboard.ScoreboardLib;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class Main extends JavaPlugin {
	
	public HashMap<Player, UUID> uuidPlayer = new HashMap<>();
	public static Main plugin;
	public final static String HTSNAME = "HtS XII";
	
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
		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
		EventManager.loadEvents(this);
		CommandsManager.loadCommands(this);
		OptionsRegister.register();
		ScoreboardLib.setPluginInstance(this);
	}	
	
	
	
	
	
	
	

    public static void sendJsonCMDMessage(Player p, String message, String cmd) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{text:\"" + message + "\",clickEvent:{action:suggest_command,value:\"" + cmd + "\"}}")));
    }
    
    
	public void executeTimer() {
		Set<Class<?>> classes = new org.reflections.Reflections("fr.HtSTeam.HtS.Options").getTypesAnnotatedWith(UsingTimer.class);
		System.out.println("found classes " + classes.size());
		for (Class<?> c : classes) {
			for (Method m : c.getMethods()) {
				try {
					if (m.isAnnotationPresent(Timer.class)) {
						Timer timer = m.getAnnotation(Timer.class);
						Object o = c.newInstance();
						if (timer.time() == getTimerMinute())
							m.invoke(o);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
