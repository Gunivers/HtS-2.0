package fr.HtSTeam.HtS.Events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import fr.HtSTeam.HtS.EnumState;

public class WaitEvent implements Listener {
	
	@EventHandler
	public void feed(FoodLevelChangeEvent e) {
		if(EnumState.getState().equals(EnumState.WAIT)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void interact(BlockBreakEvent e) {
		if(EnumState.getState().equals(EnumState.WAIT) && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void placeBlock(BlockPlaceEvent e) {
		if(EnumState.getState().equals(EnumState.WAIT) && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHealLevelChange(EntityDamageEvent e) {
		if(EnumState.getState().equals(EnumState.WAIT)) {
			e.setCancelled(true);
		}
	}

}
