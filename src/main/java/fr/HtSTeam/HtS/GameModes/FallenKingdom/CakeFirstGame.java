package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CakeFirstGame implements Listener {

	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onConcretePlace(BlockPlaceEvent e) {
		if((Tag.BANNERS.isTagged(e.getBlock().getType()) || e.getBlock().getType().equals(Material.CAKE)))
			if(e.getBlockAgainst().getType().equals(Material.YELLOW_CONCRETE) || e.getBlockAgainst().getType().equals(Material.LIME_CONCRETE) || e.getBlockAgainst().getType().equals(Material.RED_CONCRETE)) {
				e.setCancelled(false);
			}
	}
	
}
