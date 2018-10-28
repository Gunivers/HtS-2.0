package fr.HtSTeam.HtS.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;

import fr.HtSTeam.HtS.Commands.Structure.Command;
import fr.HtSTeam.HtS.Commands.Structure.CommandHandler;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Lang;

public class TeamCommand {
	
	private static ArrayList<String> colors = new ArrayList<String>();
	static { Arrays.asList(ChatColor.values()).forEach(c -> colors.add(c.name().toLowerCase())); }
	
	@CommandHandler
	public static boolean teams(Player sender, org.bukkit.command.Command cmd, String msg, String[] args) {
		if (args[0].equalsIgnoreCase("team") && args.length > 1) {
			if (args[1].equalsIgnoreCase("create") && args.length == 4) {
				if (!colors.contains(args[3]))
					return false;
				Team t = new Team(args[2], args[3]);
				sender.sendMessage(Lang.get("team.command.team.create." + ChatColor.valueOf(t.getTeamColor()) + t.getTeamName()), false);
			} else if (args[1].equalsIgnoreCase("delete") && args.length == 3) {
				if (!Team.nameTeam.containsKey(args[2]))
					return false;
				Team t = Team.nameTeam.get(args[2]);
				String str = ChatColor.valueOf(t.getTeamColor()) + t.getTeamName();
				t.delete();
				sender.sendMessage(Lang.get("team.command.team.delete." + str), false);
			} else if (args[1].equalsIgnoreCase("clear") && args.length == 3) {
				if (!Team.nameTeam.containsKey(args[2]))
					return false;
				Team t = Team.nameTeam.get(args[2]);
				String str = ChatColor.valueOf(t.getTeamColor()) + t.getTeamName();
				t.clear();
				sender.sendMessage(Lang.get("team.command.team.clear." + str), false);
			} else if (args[1].equalsIgnoreCase("list") && args.length == 2) {
				if (Team.teamList.isEmpty())
					sender.sendMessage(Lang.get("team.command.team.list.empty"), false);
				else {
					ArrayList<String> list = new ArrayList<String>();
					Team.teamList.forEach(team -> list.add(ChatColor.valueOf(team.getTeamColor()) + team.getTeamName()));
					sender.sendMessage(Lang.get("team.command.team.list." + Team.teamList.size() + "." + String.join("§r, ", list)), false);
				}
			} else
				return false;
			return true;
		} else if (args[0].equalsIgnoreCase("player") && args.length > 1) {
			if (args[1].equalsIgnoreCase("join") && args.length == 4) {
				if (!Team.nameTeam.containsKey(args[2]) || Player.instance(args[3]) == null)
					return false;
				Team t = Team.nameTeam.get(args[2]);
				Player player = Player.instance(args[3]);
				player.setTeam(t);
				if (sender != player)
					sender.sendMessage(Lang.get("team.command.player.join.sender." + ChatColor.valueOf(t.getTeamColor()) + t.getTeamName() + "." + args[3]), false);
				player.sendMessage(Lang.get("team.command.player.join." + ChatColor.valueOf(t.getTeamColor()) + t.getTeamName() + "." + args[3]), false);
			} else if (args[1].equalsIgnoreCase("leave") && args.length == 3) {
				if (Player.instance(args[2]) == null)
					return false;
				Player player = Player.instance(args[2]);
				player.setTeam(null);
				if (sender != player)
					sender.sendMessage(Lang.get("team.command.player.leave.sender." + args[3]), false);
				player.sendMessage(Lang.get("team.command.player.leave." + args[3]), false);
			} else if (args[1].equalsIgnoreCase("list") && args.length == 3) {
				if (Team.teamList.isEmpty() || !Team.nameTeam.containsKey(args[2]))
					return false;
				else if (Team.nameTeam.get(args[2]).getTeamPlayers().isEmpty())
					sender.sendMessage(Lang.get("team.command.player.list.empty"), false);
				else {
					ArrayList<String> list = new ArrayList<String>();
					Team.nameTeam.get(args[2]).getTeamPlayers().forEach(p -> list.add(p.getDisplayName()));
					sender.sendMessage(Lang.get("team.command.player.list." + Team.teamList.size() + "." + String.join("§r, ", list)), false);
				}
			} else if (args[1].equalsIgnoreCase("give") && args.length == 2) {
				for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
					player.getInventory().clear();
				for (Team t : Team.teamList)
					for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
						if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
							ItemStackBuilder a = new ItemStackBuilder(Material.BLACK_WOOL, 1, ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName(), "§fClique pour rejoindre l'équipe " + ChatColor.valueOf(t.getTeamColor().toUpperCase()) + t.getTeamName());
							a.setGlint(true);
							player.getInventory().addItem(a);
						}
			} else if (args[1].equalsIgnoreCase("random") && args.length == 2) {
				List<org.bukkit.entity.Player> onlinePlayer = new ArrayList<org.bukkit.entity.Player>();
				if(Team.teamList.isEmpty())
					return false;
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
//					while (!onlinePlayer.isEmpty())
//					for(Team team : teamList) {
//						if(onlinePlayer.isEmpty()) break; 
//						Player ran_p = onlinePlayer.get(Randomizer.randI(0, onlinePlayer.size() - 1)); 
//						team.addPlayer(ran_p); 
//						onlinePlayer.remove(ran_p); 
//					}

			} else
				return false;
			return true;
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
		prop.put("player\\|list", new ArrayList<String>(Team.nameTeam.keySet()));
		return prop;
	}
}
