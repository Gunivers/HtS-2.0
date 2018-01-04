package fr.HtSTeam.HtS.Players;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class RealisticDeath implements Listener {
	
	// DROP GOLDEN APPLE
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(PlayerDeathEvent e) {
		e.getDrops().clear();
		realisticDeath(e.getEntity(), e.getEntity().getLocation());
	}

	private void realisticDeath(Player p, Location l) {
		Player e = (Player) l.getWorld().spawnEntity(l, EntityType.PLAYER);
		e.setAI(false);
		e.setCanPickupItems(false);
		e.setCollidable(true);
		e.setCustomNameVisible(false);
		e.setGravity(true);
		e.setSilent(true);
		e.setInvulnerable(true);
		e.getEquipment().setItemInMainHand(p.getEquipment().getItemInMainHand());
		e.getEquipment().setItemInOffHand(p.getEquipment().getItemInOffHand());
		e.getEquipment().setHelmet(p.getEquipment().getHelmet());
		e.getEquipment().setChestplate(p.getEquipment().getChestplate());
		e.getEquipment().setLeggings(p.getEquipment().getLeggings());
		e.getEquipment().setBoots(p.getEquipment().getBoots());
	}
}
