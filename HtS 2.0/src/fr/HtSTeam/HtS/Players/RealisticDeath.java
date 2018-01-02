package fr.HtSTeam.HtS.Players;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
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
		realisticDeath(e.getEntity(), e.getEntity().getLocation());
	}

	private void realisticDeath(Player p, Location l) {
		 ArmorStand am = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
		 am.setArms(true);
		 am.setBasePlate(false);
		 am.setGravity(true);
		 am.setInvulnerable(true);
		 am.setItemInHand(p.getEquipment().getItemInMainHand());
		 am.setVisible(true);
	}
}
