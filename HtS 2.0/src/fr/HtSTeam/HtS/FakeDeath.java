package fr.HtSTeam.HtS;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class FakeDeath {

	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (!(p.getKiller() instanceof Creeper)) {
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			/*for (ItemStack is : playerLoot.getDrops()) {
				if (is.getData().getData() == 3 && is.getType() == Material.SKULL_ITEM) {
					SkullMeta isM = (SkullMeta) is.getItemMeta();
					isM.setOwner(p.getName());
					isM.setDisplayName("T�te de " + p.getName());
					is.setItemMeta(isM);
				}
				p.getWorld().dropItem(p.getLocation(), is);
				((Player) p).setGameMode(GameMode.SPECTATOR);
				main.players.removePlayer(p.getUniqueId());
				
				for (Player p2 : Bukkit.getOnlinePlayers()) {
					p2.playSound(p2.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
				}
			}*/
		}
	}
}
