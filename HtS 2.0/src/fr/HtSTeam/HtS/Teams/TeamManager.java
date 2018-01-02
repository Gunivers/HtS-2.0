package fr.HtSTeam.HtS.Teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamManager {
		
	public static ArrayList<TeamManager> teamList = new ArrayList<TeamManager>();
	public static Map<String, TeamManager> nameTeam = new HashMap<String, TeamManager>();
	public static Map<UUID, TeamManager> playerTeam = new HashMap<UUID, TeamManager>();
		
	private String teamName;
	private String teamColor;
	private byte teamByte;
	private boolean faketeam = false;
	
	private ArrayList<UUID> playerList = new ArrayList<UUID>();
	
	public TeamManager(String teamName, String teamColor) {
		if (nameTeam.containsKey(teamName))
			return;
		teamList.add(this);
		nameTeam.put(teamName, this);
		
		this.teamName = teamName;
		this.teamColor = teamColor;
		teamByte = getWoolByte(teamColor);
	}
	
	public void addPlayer(Player p) {
		p.sendMessage("Vous avez rejoint l'équipe " + ChatColor.valueOf(teamColor.toUpperCase()) + teamName);
		if(faketeam) {
			playerList.add(p.getUniqueId());
		} else {
			playerList.add(p.getUniqueId());
			playerTeam.put(p.getUniqueId(), this);
			p.setDisplayName(ChatColor.valueOf(teamColor.toUpperCase()) + p.getName());
			p.setPlayerListName(ChatColor.valueOf(teamColor.toUpperCase()) + p.getName());
		}
	}
	
	public void removePlayer(Player p) {
		p.sendMessage("Vous avez quitté l'équipe " + ChatColor.valueOf(teamColor.toUpperCase()) + teamName);
		if(faketeam) {
			playerList.remove(p.getUniqueId());
			if(playerList.size() == 0)
				teamList.remove(this);
		} else {
			playerList.remove(p.getUniqueId());
			playerTeam.remove(p.getUniqueId(), this);
			p.setDisplayName(p.getName());
			p.setPlayerListName(p.getName());
			if(getTeamSize() == 0) {
				teamList.remove(this);
				nameTeam.remove(teamName, this);
			}
		}
	}
	
	public void clearTeam() {
		if(faketeam) {
			playerList.clear();
			teamList.remove(this);
		} else {
			for (UUID key: playerTeam.keySet()) {
				playerTeam.remove(key, this);
			}
			playerList.clear();
			teamList.remove(this);
			nameTeam.remove(teamName, this);
		}
	}
	
	public String getTeamName() { return teamName; }
	public String getTeamColor() { return teamColor; }
	public byte getTeamByte() { return teamByte; }
	public int getTeamSize() { return playerList.size(); }
		
	public ArrayList<UUID> getTeamPlayers() { return playerList; }
	
	public boolean isFakeTeam() { return faketeam; }
	public void setFakeTeam(boolean faketeam) { this.faketeam = faketeam; }
	
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