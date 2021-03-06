package fr.HtSTeam.HtS.Teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class TeamCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("team") && p.hasPermission("team.use") && args.length > 0) {
				if (args[0].equalsIgnoreCase("add") && args.length > 2) {
					try {
						TeamBuilder tm = new TeamBuilder(args[1], args[2].toLowerCase());
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
						p.sendMessage("L'équipe " + ChatColor.valueOf(TeamBuilder.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + TeamBuilder.nameTeam.get(args[1]).getTeamName() + " §ra été supprimée !");
						TeamBuilder.nameTeam.get(args[1]).removeTeam();
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Equipe non valide !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("join") && args.length == 3) {
					try {
						TeamBuilder.nameTeam.get(args[1]).addPlayer(Bukkit.getPlayer(args[2]));
						p.sendMessage(args[2] + " a rejoint l'équipe " + ChatColor.valueOf(TeamBuilder.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + TeamBuilder.nameTeam.get(args[1]).getTeamName());
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Equipe ou joueur non valide !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("leave") && args.length == 3) {
					try {
						TeamBuilder.nameTeam.get(args[1]).removePlayer(Bukkit.getPlayer(args[2]));
						p.sendMessage(args[2] + " a quitté l'équipe " + ChatColor.valueOf(TeamBuilder.nameTeam.get(args[1]).getTeamColor().toUpperCase()) + TeamBuilder.nameTeam.get(args[1]).getTeamName());
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Equipe ou joueur non valide !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("list") && args.length == 1) {
					try {
							if (TeamBuilder.teamList.size() == 0) {
								p.sendMessage("Aucune Team n'a été créé !");
							} else {
								p.sendMessage("Liste des Teams (" + TeamBuilder.teamList.size() + ") :");
								for(TeamBuilder t :TeamBuilder.teamList)
									p.sendMessage("- " + ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName());
								return true;
							}
					} catch (NullPointerException e) {
						p.sendMessage("§4Aucunes équipes existantes !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("list") && args.length == 2) {
					try {
						TeamBuilder t = TeamBuilder.nameTeam.get(args[1]);
						p.sendMessage("Joueurs de la team " + ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName() + " : " );
						for(UUID uuid : t.getTeamPlayers())
							p.sendMessage("- " + Bukkit.getPlayer(uuid).getName());
						return true;
					} catch (NullPointerException e) {
						p.sendMessage("§4Equipe inexistante !");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("give")) {
					try {
						for (Player player : Bukkit.getOnlinePlayers())
							player.getInventory().clear();
						for (TeamBuilder t : TeamBuilder.teamList)
							for (Player player : Bukkit.getOnlinePlayers())
								if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
									ItemStackBuilder a = new ItemStackBuilder(Material.WOOL, t.getTeamByte(), 1, ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName(), "§fClique pour rejoindre l'équipe " + ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName());
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
					if(TeamBuilder.teamList.size() != 0) {
						for (Player co : Bukkit.getOnlinePlayers())
							if (co.getGameMode() != GameMode.SPECTATOR)
								onlinePlayer.add(co);
								
						ArrayList<TeamBuilder> teamList = new ArrayList<TeamBuilder>();
						int size = TeamBuilder.teamList.size();
						for (int i = 0; i < size; i++)
							if(!TeamBuilder.teamList.get(i).isFakeTeam()) {
								TeamBuilder.teamList.get(i).clearTeam();
								teamList.add(TeamBuilder.teamList.get(i));
							}
						
						while (!onlinePlayer.isEmpty())
							for(TeamBuilder team : teamList) {
								if(onlinePlayer.isEmpty()) break; 
								Player ran_p = onlinePlayer.get(Randomizer.RandI(0, onlinePlayer.size() - 1)); 
								team.addPlayer(ran_p); 
								onlinePlayer.remove(ran_p); 
							}
						
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
