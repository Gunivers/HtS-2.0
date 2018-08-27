package fr.HtSTeam.HtS.Teams;

import org.bukkit.ChatColor;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamGive implements Listener {
	
	@EventHandler
	public void onWoolClicked(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getItem() != null && Tag.WOOL.isTagged(e.getItem().getType()) && e.getItem().getItemMeta().hasEnchants()) {
				e.setCancelled(true);
				if (TeamBuilder.playerTeam.containsKey(e.getPlayer().getUniqueId())) {
					if (TeamBuilder.playerTeam.get(e.getPlayer().getUniqueId()).getTeamSize() > 1) {
						TeamBuilder.playerTeam.get(e.getPlayer().getUniqueId()).removePlayer(e.getPlayer());
					} else {
						e.getPlayer().sendMessage("Vous ne pouvez pas quitter l'équipe "+ ChatColor.valueOf(TeamBuilder.playerTeam.get(e.getPlayer().getUniqueId()).getTeamColor().toUpperCase())+ TeamBuilder.playerTeam.get(e.getPlayer().getUniqueId()).getTeamName());
						return;
					}
				}

				if (TeamBuilder.nameTeam.containsKey(e.getItem().getItemMeta().getDisplayName().substring(2)))
					TeamBuilder.nameTeam.get(e.getItem().getItemMeta().getDisplayName().substring(2)).addPlayer(e.getPlayer());
			}
		}
	}
}
