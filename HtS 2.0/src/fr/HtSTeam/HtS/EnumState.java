package fr.HtSTeam.HtS;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public enum EnumState implements Listener {
	
	WAIT,
	RUNNING,
	FINISHING;
	
	private EnumState state;
	
	public void setState(EnumState state) {
		this.state = state;
		if(state.equals(EnumState.WAIT)) {
			for (World world : Bukkit.getWorlds())
				world.setPVP(false);
		}
	}
	
	public EnumState getState() { return state; }
	
	
	
	
	
	
	@EventHandler
	public void feed(FoodLevelChangeEvent e) {
		if(state.equals(EnumState.WAIT)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void interact(BlockBreakEvent e) {
		if(state.equals(EnumState.WAIT)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHealLevelChange(EntityDamageEvent e) {
		if(state.equals(EnumState.WAIT)) {
			e.setCancelled(true);
		}
	}
	
}

