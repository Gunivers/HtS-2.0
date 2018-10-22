package fr.HtSTeam.HtS.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.RadarFrequencyOption;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;

public class JoinLeaveEvent {
	
	@EventHandler
	public static void onJoin(PlayerJoinEvent e) {
//		Player p = e.getPlayer();
		
//		if (Team.teamList.size() != 0 && p.getGameMode() != GameMode.SPECTATOR && Team.playerTeam.containsKey(p.getUniqueId())) {
//			p.setDisplayName(ChatColor.valueOf(Team.playerTeam.get(p.getUniqueId()).getTeamColor().toUpperCase()) + p.getName() + "§r");
//			p.setPlayerListName(ChatColor.valueOf(Team.playerTeam.get(p.getUniqueId()).getTeamColor().toUpperCase()) + p.getName());
//		}
	}
	
	@EventHandler
	public static void onLeave(PlayerQuitEvent e) {
		if(EnumState.getState() == EnumState.RUNNING)
			if (Main.gamemode instanceof SyT)
				RadarFrequencyOption.offlineLocation.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
	}
}
