package fr.HtSTeam.HtS;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class FakeDeath {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		for (ItemStack is : Main.deathLoot.getDeathLoot()) {
			if (is.getData().getData() == 3 && is.getType() == Material.SKULL_ITEM) {
				SkullMeta isM = (SkullMeta) is.getItemMeta();
				isM.setOwner(p.getName());
				isM.setDisplayName("Tête de " + p.getName());
				is.setItemMeta(isM);
			}
			p.getWorld().dropItem(p.getLocation(), is);
			((Player) p).setGameMode(GameMode.SPECTATOR);
			Main.playerInGame.removePlayer(p);

			for (Player p2 : Bukkit.getOnlinePlayers())
				p2.playSound(p2.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
		}
	}
}
