package fr.HtSTeam.HtS.Teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public class TeamCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("team") && p.hasPermission("team.use") && args.length > 0) {
				if (args[0].equalsIgnoreCase("add") && args.length > 2) {
					try {
						Team tm = new Team(args[1], args[2].toLowerCase());
						if (args.length == 4)
							tm.setFakeTeam(Boolean.valueOf(args[3]));
						p.sendMessage("L'équipe " + ChatColor.valueOf(tm.getTeamColor().toUpperCase()) + tm.getTeamName() + " §ra été créée !");
						return true;
					} catch (Exception e){
						p.sendMessage("§4Erreur !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
					try {
						p.sendMessage("L'équipe " + ChatColor.valueOf(Team.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + Team.nameTeam.get(args[1]).getTeamName() + " §ra été supprimée !");
						Team.nameTeam.get(args[1]).delete();
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Equipe non valide !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("join") && args.length == 3) {
					try {
//						Team.nameTeam.get(args[1]).add(Bukkit.getPlayer(args[2]));
						p.sendMessage(args[2] + " a rejoint l'équipe " + ChatColor.valueOf(Team.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + Team.nameTeam.get(args[1]).getTeamName());
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Equipe ou joueur non valide !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("leave") && args.length == 3) {
					try {
//						Team.nameTeam.get(args[1]).removePlayer(Bukkit.getPlayer(args[2]));
						p.sendMessage(args[2] + " a quitté l'équipe " + ChatColor.valueOf(Team.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + Team.nameTeam.get(args[1]).getTeamName());
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Equipe ou joueur non valide !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("list") && args.length == 1) {
					try {
							if (Team.teamList.size() == 0) {
								p.sendMessage("Aucune Team n'a été créé !");
							} else {
								p.sendMessage("Liste des Teams (" + Team.teamList.size() + ") :");
								for(Team t :Team.teamList)
									p.sendMessage("- " + ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName());
								return true;
							}
					} catch (NullPointerException e) {
						p.sendMessage("§4Aucunes équipes existantes !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("list") && args.length == 2) {
					try {
						Team t = Team.nameTeam.get(args[1]);
						p.sendMessage("Joueurs de la team " + ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName() + " : " );
						for(fr.HtSTeam.HtS.Player.Player p2 : t.getTeamPlayers())
							p.sendMessage("- " + p2.getName());
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Equipe inexistante !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("give")) {
					try {
						for (Player player : Bukkit.getOnlinePlayers())
							player.getInventory().clear();
						for (Team t : Team.teamList)
							for (Player player : Bukkit.getOnlinePlayers())
								if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
									ItemStackBuilder a = new ItemStackBuilder(Material.BLACK_WOOL, 1, ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName(), "§fClique pour rejoindre l'équipe " + ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName());
									a.setGlint(true);
									player.getInventory().addItem(a);
								}
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Aucunes équipes existantes !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("random")) {
					List<Player> onlinePlayer = new ArrayList<Player>();
					if(Team.teamList.size() != 0) {
						for (Player co : Bukkit.getOnlinePlayers())
							if (co.getGameMode() != GameMode.SPECTATOR)
								onlinePlayer.add(co);
								
						ArrayList<Team> teamList = new ArrayList<Team>();
						int size = Team.teamList.size();
						for (int i = 0; i < size; i++)
							if(!Team.teamList.get(i).isFakeTeam()) {
								Team.teamList.get(i).clear();
								teamList.add(Team.teamList.get(i));
							}
						
						while (!onlinePlayer.isEmpty())
//							for(Team team : teamList) {
//								if(onlinePlayer.isEmpty()) break; 
//								Player ran_p = onlinePlayer.get(Randomizer.randI(0, onlinePlayer.size() - 1)); 
//								team.addPlayer(ran_p); 
//								onlinePlayer.remove(ran_p); 
//							}
						
						return true;
					}
					p.sendMessage("§4Aucunes équipes existantes !");
					return false;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
