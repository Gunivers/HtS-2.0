package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Utils.Randomizer;

public class ChargedCreeper implements Listener {
	
	
	@EventHandler
	public void onCreeperSpawn(CreatureSpawnEvent e) {
		if(e.getEntityType().equals(EntityType.CREEPER))
			if(Randomizer.randRate(10)) {
				Creeper c = (Creeper) e.getEntity();
				c.setPowered(true);
			}
	}
	
	@EventHandler
	public void onChargedCreeperDeath(EntityDeathEvent e) {
		if(e.getEntity() instanceof Creeper) {
			if(((Creeper) e.getEntity()).isPowered()) {
				e.getDrops().clear();
				e.getDrops().add(new ItemStack(Material.TNT));
			}
		}
	}

}
