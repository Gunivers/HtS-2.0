package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BasesEvent implements Listener {	
	
	public List<Material> authorizedBlock = new ArrayList<Material>();
	
	public BasesEvent() {
		authorizedBlock.add(Material.TORCH);
		authorizedBlock.add(Material.TNT);
		authorizedBlock.add(Material.WALL_SIGN);
		authorizedBlock.add(Material.SIGN_POST);
		authorizedBlock.add(Material.FIRE);
		authorizedBlock.add(Material.LEVER);
		authorizedBlock.add(Material.REDSTONE_TORCH_ON);
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		if(PlayerBase.isInBase(e.getPlayer(), e.getBlock().getLocation()).equals(PlayerBase.OTHER))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onPoseBlock(BlockPlaceEvent e) {
		if(!PlayerBase.isInBase(e.getPlayer(), e.getBlock().getLocation()).equals(PlayerBase.OWN))
			if(!authorizedBlock.contains(e.getBlock().getType()))
				e.setCancelled(true);
		
	}
	
	
	@EventHandler
	public void onEnterInBase(PlayerMoveEvent e) {
		//if(e)
	}

}
