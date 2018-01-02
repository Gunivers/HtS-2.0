package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.HtSTeam.HtS.Utils.ActionBar;

public class BasesEvent implements Listener {	
	
	public List<Material> authorizedBlock = new ArrayList<Material>();
	private Map<UUID, PlayerBase> playerLocation = new HashMap<UUID, PlayerBase>();	
	
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
	public void onSwitchBase(PlayerMoveEvent e) {
		if (!playerLocation.containsKey(e.getPlayer().getUniqueId())) {
			playerLocation.put(e.getPlayer().getUniqueId(), PlayerBase.isInBase(e.getPlayer(), e.getPlayer().getLocation()));
		} else {
			if (!PlayerBase.isInBase(e.getPlayer(), e.getPlayer().getLocation()).equals(playerLocation.get(e.getPlayer().getUniqueId()))) {
				
				if(PlayerBase.isInBase(e.getPlayer(), e.getPlayer().getLocation()).equals(PlayerBase.OTHER))
					new ActionBar(e.getPlayer(), "§4MESSAGE ENTER ENEMY BASE", 5).send();
				
				if(PlayerBase.isInBase(e.getPlayer(), e.getPlayer().getLocation()).equals(PlayerBase.OWN))
					new ActionBar(e.getPlayer(), "§4MESSAGE ENTER OWN BASE", 5).send();
				
				if(PlayerBase.isInBase(e.getPlayer(), e.getPlayer().getLocation()).equals(PlayerBase.NONE))
					new ActionBar(e.getPlayer(), "§4MESSAGE ENTER NO MANS LAND", 5).send();
				
				playerLocation.put(e.getPlayer().getUniqueId(), PlayerBase.isInBase(e.getPlayer(), e.getPlayer().getLocation()));
			}
		}
	}
}
