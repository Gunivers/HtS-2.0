package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CakeFirstGame implements Listener {

	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onConcretePlace(BlockPlaceEvent e) {
		if(e.getBlock().getType().equals(Material.CAKE_BLOCK) && e.getBlockAgainst().getType().equals(Material.CONCRETE))
			if(e.getBlockAgainst().getData() == 4 || e.getBlockAgainst().getData() == 5 || e.getBlockAgainst().getData() == 14) {
				e.setCancelled(false);
			}
	}
	
}
