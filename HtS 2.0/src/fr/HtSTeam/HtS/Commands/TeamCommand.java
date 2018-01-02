package fr.HtSTeam.HtS.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Teams.TeamManager;
import fr.HtSTeam.HtS.Utils.Randomizer;
import net.md_5.bungee.api.ChatColor;

public class TeamCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("team") && p.hasPermission("team.use") && args.length > 0) {
				if (args[0].equalsIgnoreCase("add") && args.length > 2) {
					TeamManager tm = new TeamManager(args[1], args[2].toLowerCase());
					if (args.length == 4)
						tm.setFakeTeam(Boolean.valueOf(args[3]));
					p.sendMessage("L'équipe " + ChatColor.valueOf(tm.getTeamColor().toUpperCase()) + tm.getTeamName() + " §ra été créée !");
					return true;
				} else if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
					if (TeamManager.nameTeam.containsKey(args[1])) {
						TeamManager.nameTeam.get(args[1]).clearTeam();
						p.sendMessage("L'équipe " + ChatColor.valueOf(TeamManager.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + TeamManager.nameTeam.get(args[1]).getTeamName() + " §ra été supprimée !");
						return true;
					}
					return false;
				} else if (args[0].equalsIgnoreCase("join") && args.length == 3) {
					if (TeamManager.nameTeam.containsKey(args[1])) {
						TeamManager.nameTeam.get(args[1]).addPlayer(Bukkit.getPlayer(args[2]));
						p.sendMessage(args[2] + " a rejoint l'équipe " + ChatColor.valueOf(TeamManager.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + TeamManager.nameTeam.get(args[1]).getTeamName());
						return true;
					}
					return false;
				} else if (args[0].equalsIgnoreCase("leave") && args.length == 2) {
					if (TeamManager.nameTeam.containsKey(args[1])) {
						TeamManager.playerTeam.get(Bukkit.getPlayer(args[1])).removePlayer(Bukkit.getPlayer(args[1]));
						p.sendMessage(args[2] + " a quitté l'équipe " + ChatColor.valueOf(TeamManager.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + TeamManager.nameTeam.get(args[1]).getTeamName());
						return true;
					}
					return false;
				} else if (args[0].equalsIgnoreCase("list") && args.length == 1) {
					p.sendMessage("Liste des Teams:");
					for(TeamManager t :TeamManager.teamList)
						p.sendMessage("- " + ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName());
					return true;
				} else if (args[0].equalsIgnoreCase("random")) {
					if(TeamManager.teamList.size() != 0) {
						ArrayList<TeamManager> teamList = new ArrayList<TeamManager>();
						for (TeamManager t : TeamManager.teamList) {	
							if(!t.isFakeTeam())
								teamList.add(t);
						}
						for (Player player : Bukkit.getOnlinePlayers()) {
							TeamManager team = teamList.get(Randomizer.RandI(0, teamList.size() - 1));
							team.addPlayer(player);
							if (team.getTeamSize() == (int) (Bukkit.getOnlinePlayers().size()/teamList.size()))
								teamList.remove(team);
							if (teamList.size() == 0)
								break;
						}
					}
					return false;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
