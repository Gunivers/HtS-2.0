package fr.HtSTeam.HtS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

public class TeamManager {
		
	public static ArrayList<TeamManager> teamList = new ArrayList<TeamManager>();
	public static Map<Player, TeamManager> playerTeam = new HashMap<Player, TeamManager>();
	
	private Team team;
	
	private String teamName;
	private String teamColor;
	private byte teamByte;
		
	public TeamManager(String teamName, String teamColor) {
		teamList.add(this);
		
		this.teamName = teamName;
		this.teamColor = teamColor;
		teamByte = getWoolByte(teamColor);
		
		team = Main.b.registerNewTeam(teamName);
		team.setColor(ChatColor.valueOf(teamColor.toUpperCase()));
	}
	
	public void addPlayer(Player p) {
		playerTeam.put(p, this);
		team.addEntry(p.getName());
	}
	
	public void removePlayer(Player p) {
		playerTeam.remove(p, this);
		team.removeEntry(p.getName());
	}
	
	public void clearTeam() {
		for (String entry : team.getEntries()) {
			playerTeam.remove(Bukkit.getPlayer(entry), this);
			team.removeEntry(entry);
		}
	}
	
	public void setTeamFriendlyFire(boolean friendlyfire) {
		team.setAllowFriendlyFire(friendlyfire);
	}
	
	public void setTeamNameTag(OptionStatus visible) {
		team.setOption(Option.NAME_TAG_VISIBILITY, visible);
	}
	
	public String getTeamName() { return teamName; }
	public String getTeamColor() { return teamColor; }
	public byte getTeamByte() { return teamByte; }
	public int getTeamSize() { return team.getSize(); }
	
	 private byte getWoolByte(String color) {	
	    	switch(color) {
	    		case "white":
	    			return 0;
	    		case "gold":
	    			return 1;
	    		case "dark_red":
	    			return 2;
	    		case "aqua":
	    			return 3;
	    		case "yellow":
	    			return 4;
	    		case "green":
	    			return 5;
	    		case "light_purple":
	    			return 6;
	    		case "dark_gray":
	    			return 7;
	    		case "gray":
	    			return 8;
	    		case "dark_aqua":
	    			return 9;
	    		case "dark_purple":
	    			return 10;
	    		case "dark_blue":
	    			return 11;
	    		case "brown":
	    			return 12;
	    		case "dark_green":
	    			return 13;
	    		case "red":
	    			return 14;
	    		case "black":
	    			return 15;
	    	}
	    	return 0;	
	    }
}
