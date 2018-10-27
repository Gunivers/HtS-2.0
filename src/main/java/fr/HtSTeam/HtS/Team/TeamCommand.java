package fr.HtSTeam.HtS.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import fr.HtSTeam.HtS.Commands.Structure.Command;
import fr.HtSTeam.HtS.Commands.Structure.CommandHandler;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Lang;

public class TeamCommand {
	
	private static ArrayList<String> colors = new ArrayList<String>();
	static { Arrays.asList(ChatColor.values()).forEach(c -> colors.add(c.name().toLowerCase())); }
	
	@CommandHandler
	public static boolean teams(Player p , org.bukkit.command.Command cmd, String msg, String[] args) {
		if (args[0].equalsIgnoreCase("team") && args.length > 1) {
			if (args[1].equalsIgnoreCase("create") && args.length == 4) {
				if (!colors.contains(args[3]))
					return false;
				Team t = new Team(args[2], args[3]);
				p.sendMessage(Lang.get("team.command.team.create." + ChatColor.valueOf(t.getTeamColor()) + t.getTeamName()), false);
			} else if (args[1].equalsIgnoreCase("delete") && args.length == 3) {
				if (!Team.nameTeam.containsKey(args[2]))
					return false;
				Team t = Team.nameTeam.get(args[2]);
				String str = ChatColor.valueOf(t.getTeamColor()) + t.getTeamName();
				t.delete();
				p.sendMessage(Lang.get("team.command.team.delete." + str), false);
			} else if (args[1].equalsIgnoreCase("delete") && args.length == 3) {
				if (!Team.nameTeam.containsKey(args[2]))
					return false;
				Team t = Team.nameTeam.get(args[2]);
				String str = ChatColor.valueOf(t.getTeamColor()) + t.getTeamName();
				t.clear();
				p.sendMessage(Lang.get("team.command.team.clear." + str), false);
			} else if (args[1].equalsIgnoreCase("list") && args.length == 2) {
				if (Team.teamList.isEmpty())
					p.sendMessage(Lang.get("team.command.team.list.empty"), false);
				else {
					ArrayList<String> list = new ArrayList<String>();
					Team.teamList.forEach(team -> list.add(ChatColor.valueOf(team.getTeamColor()) + team.getTeamName()));
					p.sendMessage(Lang.get("team.command.team.list." + Team.teamList.size() + "." + String.join("§r, ", list)), false);
				}
			} else
				return false;
			return true;
		} else if (args[0].equalsIgnoreCase("player") && args.length > 1) {

		}
		return false;
	}
	
	@SuppressWarnings("serial")
	@CommandHandler(Command.Commands.COMPLETE)
	public static HashMap<String,ArrayList<String>> teams()
	{
		HashMap<String,ArrayList<String>> prop = new HashMap<String,ArrayList<String>>();
		prop.put(null, new ArrayList<String>() {{ add("team"); add("player"); }});
		prop.put("team", new ArrayList<String>() {{ add("clear"); add("create"); add("delete"); add("list"); }});
		prop.put("team\\|create\\|\\w[^| ]*", colors);
		prop.put("team\\|delete", new ArrayList<String>(Team.nameTeam.keySet()));
		prop.put("team\\|clear", new ArrayList<String>(Team.nameTeam.keySet()));
		prop.put("player", new ArrayList<String>() {{ add("join"); add("leave"); add("random"); add("give"); add("list"); }});
		prop.put("player\\|join", new ArrayList<String>(Team.nameTeam.keySet()));
		return prop;
	}
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			org.bukkit.entity.Player p = (org.bukkit.entity.Player) sender;
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
						for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
							player.getInventory().clear();
						for (Team t : Team.teamList)
							for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
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
					List<org.bukkit.entity.Player> onlinePlayer = new ArrayList<org.bukkit.entity.Player>();
					if(Team.teamList.size() != 0) {
						for (org.bukkit.entity.Player co : Bukkit.getOnlinePlayers())
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
