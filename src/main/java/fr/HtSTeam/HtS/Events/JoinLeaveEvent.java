package fr.HtSTeam.HtS.Events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.RadarFrequencyOption;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;
import fr.HtSTeam.HtS.Teams.TeamBuilder;

public class JoinLeaveEvent {
	
	@EventHandler
	public static void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (TeamBuilder.teamList.size() != 0 && p.getGameMode() != GameMode.SPECTATOR && TeamBuilder.playerTeam.containsKey(p.getUniqueId())) {
			p.setDisplayName(ChatColor.valueOf(TeamBuilder.playerTeam.get(p.getUniqueId()).getTeamColor().toUpperCase()) + p.getName() + "§r");
			p.setPlayerListName(ChatColor.valueOf(TeamBuilder.playerTeam.get(p.getUniqueId()).getTeamColor().toUpperCase()) + p.getName());
		}
		if (EnumState.getState() == EnumState.RUNNING)
			ScoreBoard.send(p);	
	}
	
	@EventHandler
	public static void onLeave(PlayerQuitEvent e) {
		if(EnumState.getState() == EnumState.RUNNING) {
			ScoreBoard.scoreboards.remove(e.getPlayer().getUniqueId());
			if (Main.gamemode instanceof SyT)
				RadarFrequencyOption.offlineLocation.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
		}
	}
}
