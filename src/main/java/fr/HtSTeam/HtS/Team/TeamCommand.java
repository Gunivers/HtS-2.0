package fr.HtSTeam.HtS.Team;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;

import fr.HtSTeam.HtS.Commands.Structure.Command;
import fr.HtSTeam.HtS.Commands.Structure.CommandHandler;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ColorUtil;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Lang;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class TeamCommand {
	
	@CommandHandler
	public static boolean teams(Player sender, org.bukkit.command.Command cmd, String msg, String[] args) {
		if (args[0].equalsIgnoreCase("team") && args.length > 1) {
			if (args[1].equalsIgnoreCase("create") && args.length == 4) {
				if (!ColorUtil.getChatColors().contains(args[3]))
					return false;
				Team t = new Team(args[2], args[3]);
				sender.sendMessage(Lang.get("team.command.team.create").replaceAll(Lang.REPLACE, ChatColor.valueOf(t.getTeamColor()) + t.getTeamName()), false);
			} else if (args[1].equalsIgnoreCase("delete") && args.length == 3) {
				if (!Team.nameTeam.containsKey(args[2]))
					return false;
				Team t = Team.nameTeam.get(args[2]);
				t.delete();
				sender.sendMessage(Lang.get("team.command.team.delete").replaceAll(Lang.REPLACE, ChatColor.valueOf(t.getTeamColor()) + t.getTeamName()), false);
			} else if (args[1].equalsIgnoreCase("clear") && args.length == 3) {
				if (!Team.nameTeam.containsKey(args[2]))
					return false;
				Team t = Team.nameTeam.get(args[2]);
				t.clear();
				sender.sendMessage(Lang.get("team.command.team.clear".replaceAll(Lang.REPLACE, ChatColor.valueOf(t.getTeamColor()) + t.getTeamName())), false);
			} else if (args[1].equalsIgnoreCase("list") && args.length == 2) {
				if (Team.teamList.isEmpty())
					sender.sendMessage(Lang.get("team.command.team.list.empty"), false);
				else {
					ArrayList<String> list = new ArrayList<String>();
					Team.teamList.forEach(team -> list.add(ChatColor.valueOf(team.getTeamColor()) + team.getTeamName()));
					if (list.size() == 1)
						sender.sendMessage(Lang.get("team.command.team.list.1").replaceAll(Lang.REPLACE, String.join("§r, ", list)), false);
					else
						sender.sendMessage(Lang.get("team.command.team.list").replaceFirst(Lang.REPLACE, Integer.toString(list.size())).replaceAll(Lang.REPLACE, String.join("§r, ", list)), false);
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
					sender.sendMessage(Lang.get("team.command.player.join.sender").replaceFirst(Lang.REPLACE, ChatColor.valueOf(t.getTeamColor()) + t.getTeamName()).replaceAll(Lang.REPLACE, args[3]), false);
				player.sendMessage(Lang.get("team.command.player.join").replaceFirst(Lang.REPLACE, ChatColor.valueOf(t.getTeamColor()) + t.getTeamName()).replaceAll(Lang.REPLACE, args[3]), false);
			} else if (args[1].equalsIgnoreCase("leave") && args.length == 3) {
				if (Player.instance(args[2]) == null)
					return false;
				Player player = Player.instance(args[2]);
				player.setTeam(null);
				if (sender != player)
					sender.sendMessage(Lang.get("team.command.player.leave.sender").replaceAll(Lang.REPLACE, args[3]), false);
				player.sendMessage(Lang.get("team.command.player.leave.sender").replaceAll(Lang.REPLACE, args[3]), false);
			} else if (args[1].equalsIgnoreCase("list") && args.length == 3) {
				if (Team.teamList.isEmpty() || !Team.nameTeam.containsKey(args[2]))
					return false;
				else if (Team.nameTeam.get(args[2]).getTeamPlayers().isEmpty())
					sender.sendMessage(Lang.get("team.command.player.list.empty"), false);
				else {
					ArrayList<String> list = new ArrayList<String>();
					Team.nameTeam.get(args[2]).getTeamPlayers().forEach(p -> list.add(p.getDisplayName()));
					if (list.size() == 1)
						sender.sendMessage(Lang.get("team.command.player.list.1").replaceAll(Lang.REPLACE, String.join("§r, ", list)), false);
					else
						sender.sendMessage(Lang.get("team.command.player.list").replaceFirst(Lang.REPLACE, Integer.toString(list.size())).replaceAll(Lang.REPLACE, String.join("§r, ", list)), false);
				}
			} else if (args[1].equalsIgnoreCase("give") && args.length == 2) {
				Player.forEach(player -> player.getInventory().clear());
				for (Team t : Team.teamList) {
					ItemStackBuilder itm = new ItemStackBuilder(ColorUtil.chatColorToWoolMaterial(t.getTeamColor()), 1, ChatColor.valueOf(t.getTeamColor()) + t.getTeamName(), Lang.get("team.command.player.give").replaceAll(Lang.REPLACE, ChatColor.valueOf(t.getTeamColor()) + t.getTeamName()));
					itm.setGlint(true);
					Player.forEach(player -> { if (player.isSpectator()) return; player.getInventory().addItem(itm);});
				}
			} else if (args[1].equalsIgnoreCase("random") && args.length == 2) {
				if(Team.teamList.isEmpty())
					return false;
				
				ArrayList<Player> players = new ArrayList<Player>();
				Player.forEach(player -> { if (!player.isSpectator()) players.add(player); });			
				ArrayList<Team> teamList = new ArrayList<Team>();
				for (int i = 0; i < Team.teamList.size(); i++)
					if(!Team.teamList.get(i).isFakeTeam()) {
						Team.teamList.get(i).clear();
						teamList.add(Team.teamList.get(i));
					}
				while (!players.isEmpty())
					for(Team team : teamList) {
						if(players.isEmpty()) break; 
						Player ran_p = players.get(Randomizer.randI(0, players.size() - 1)); 
						ran_p.setTeam(team); 
						ran_p.sendMessage(Lang.get("team.command.player.join").replaceFirst(Lang.REPLACE, ChatColor.valueOf(team.getTeamColor()) + team.getTeamName()).replaceAll(Lang.REPLACE, args[3]), false);
						players.remove(ran_p); 
					}

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
		prop.put("team\\|create\\|\\w[^| ]*", ColorUtil.getChatColors());
		prop.put("team\\|delete", new ArrayList<String>(Team.nameTeam.keySet()));
		prop.put("team\\|clear", new ArrayList<String>(Team.nameTeam.keySet()));
		prop.put("player", new ArrayList<String>() {{ add("join"); add("leave"); add("random"); add("give"); add("list"); }});
		prop.put("player\\|join", new ArrayList<String>(Team.nameTeam.keySet()));
		prop.put("player\\|list", new ArrayList<String>(Team.nameTeam.keySet()));
		return prop;
	}
}
