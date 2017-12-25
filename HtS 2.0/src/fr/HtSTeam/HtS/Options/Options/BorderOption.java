package fr.HtSTeam.HtS.Options.Options;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class BorderOption extends OptionsManager {
	
	public BorderOption() {
		super(Material.IRON_BARDING, "Taille de la bordure", "1000*1000", "500", OptionsRegister.uhc);
	}

	public static UUID playerBorder = null;

	/*@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		Bukkit.broadcastMessage("Test");
		
	}*/

	@Override
	public void event(Player p) {
		Bukkit.broadcastMessage("Test");
		
	}

}
