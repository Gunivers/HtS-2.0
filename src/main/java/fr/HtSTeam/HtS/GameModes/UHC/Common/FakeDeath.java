package fr.HtSTeam.HtS.GameModes.UHC.Common;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Players.PlayerRemove;

public class FakeDeath implements Listener, PlayerRemove {
	
	{
		addToList();
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		for (ItemStack is : Main.deathLoot.getDeathLoot()) {
			if (is.getType() == Material.PLAYER_HEAD) {
				SkullMeta isM = (SkullMeta) is.getItemMeta();
				isM.setOwningPlayer(Bukkit.getPlayer(p.getUniqueId()));
				isM.setDisplayName("Tête de " + p.getName());
				is.setItemMeta(isM);
			}
			p.getWorld().dropItem(p.getLocation(), is);
		}
		((Player) p).setGameMode(GameMode.SPECTATOR);
		removePlayer(p.getUniqueId(), p.getName());
	}
	
	public void removePlayer(UUID uuid, String name) {
		PlayerInGame.removeFromGame(uuid);
		boolean isOffline = true;
		for (Player p2 : Bukkit.getOnlinePlayers()) {
			p2.playSound(p2.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
			if(p2.getUniqueId().equals(uuid)) isOffline = false;
		}
		if(isOffline) Bukkit.broadcastMessage(name + " est mort.");
	}
}
