package fr.HtSTeam.HtS.Teams;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamGive implements Listener {
	
	@EventHandler
	public void onWoolClicked(PlayerInteractEvent e) {
		if (e.getItem().getType().equals(Material.WOOL) && e.getItem().getItemMeta().hasEnchants() && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			e.setCancelled(true);
			if (TeamManager.playerTeam.containsKey(e.getPlayer().getUniqueId())) {
				if (TeamManager.playerTeam.get(e.getPlayer().getUniqueId()).getTeamSize() == 1) {
					TeamManager.playerTeam.get(e.getPlayer().getUniqueId()).removePlayer(e.getPlayer());
				} else {
					e.getPlayer().sendMessage("Vous ne pouvez pas quitter l'équipe " + ChatColor.valueOf(TeamManager.playerTeam.get(e.getPlayer().getUniqueId()).getTeamColor().toUpperCase()) + TeamManager.playerTeam.get(e.getPlayer().getUniqueId()).getTeamName());
					return;
				}
			}
			
			if (TeamManager.nameTeam.containsKey(e.getItem().getItemMeta().getDisplayName().substring(2)))
				TeamManager.nameTeam.get(e.getItem().getItemMeta().getDisplayName().substring(2)).addPlayer(e.getPlayer());
		}
	}
}
