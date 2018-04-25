package fr.HtSTeam.HtS.Players.Spectator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CustomChat implements Listener {


	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
			String messageS = e.getMessage();
			for(Player p : Bukkit.getOnlinePlayers())
				if(p.getGameMode() == GameMode.SPECTATOR)
					p.sendMessage("§7§o[Spectateur] " + e.getPlayer().getName() + " : " + messageS);
			
			e.setCancelled(true);
		}
	}
	
}
