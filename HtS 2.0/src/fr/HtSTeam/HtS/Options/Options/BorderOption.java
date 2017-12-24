package fr.HtSTeam.HtS.Options.Options;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.OptionsManager;

public class BorderOption extends OptionsManager {
	
	public BorderOption() {
		super("Taille de la bordure", "1000*1000", Material.IRON_BARDING, "500");
	}

	public static UUID playerBorder = null;

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		Bukkit.broadcastMessage("Test");
		
	}

}
