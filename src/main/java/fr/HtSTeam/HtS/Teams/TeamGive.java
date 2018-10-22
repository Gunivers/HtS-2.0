package fr.HtSTeam.HtS.Teams;

import org.bukkit.event.Listener;

public class TeamGive implements Listener {
	
//	@EventHandler
//	public void onWoolClicked(PlayerInteractEvent e) {
//		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//			if (e.getItem() != null && Tag.WOOL.isTagged(e.getItem().getType()) && e.getItem().getItemMeta().hasEnchants()) {
//				e.setCancelled(true);
//				if (Team.playerTeam.containsKey(e.getPlayer().getUniqueId())) {
//					if (Team.playerTeam.get(e.getPlayer().getUniqueId()).getTeamSize() > 1) {
//						Team.playerTeam.get(e.getPlayer().getUniqueId()).removePlayer(e.getPlayer());
//					} else {
//						e.getPlayer().sendMessage("Vous ne pouvez pas quitter l'équipe "+ ChatColor.valueOf(Team.playerTeam.get(e.getPlayer().getUniqueId()).getTeamColor().toUpperCase())+ Team.playerTeam.get(e.getPlayer().getUniqueId()).getTeamName());
//						return;
//					}
//				}
//
//				if (Team.nameTeam.containsKey(e.getItem().getItemMeta().getDisplayName().substring(2)))
//					Team.nameTeam.get(e.getItem().getItemMeta().getDisplayName().substring(2)).addPlayer(e.getPlayer());
//			}
//		}
//	}
}
