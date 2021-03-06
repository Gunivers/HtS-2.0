package fr.HtSTeam.HtS.Teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;

public class TeamBuilder implements StartTrigger {
		
	public static ArrayList<TeamBuilder> teamList = new ArrayList<TeamBuilder>();
	public static Map<String, TeamBuilder> nameTeam = new HashMap<String, TeamBuilder>();
	public static Map<UUID, TeamBuilder> playerTeam = new HashMap<UUID, TeamBuilder>();
		
	private String teamName;
	private String teamColor;
	private byte teamByte;
	private boolean faketeam = false;
	
	private ArrayList<UUID> allPlayers = new ArrayList<UUID>();
	private ArrayList<UUID> playerList = new ArrayList<UUID>();
	
	public TeamBuilder(String teamName, String teamColor) {
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
			p.setDisplayName(ChatColor.valueOf(teamColor.toUpperCase()) + p.getName() + "§r");
			p.setPlayerListName(ChatColor.valueOf(teamColor.toUpperCase()) + p.getName());
		}
	}
	
	public void removePlayer(Player p) {
		if (!EnumState.getState().equals(EnumState.RUNNING))
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

	public void removePlayer(UUID uuid) {
		if (faketeam) {
			playerList.remove(uuid);
			if (playerList.size() == 0)
				teamList.remove(this);
		} else {
			playerList.remove(uuid);
			playerTeam.remove(uuid, this);
			if (getTeamSize() == 0) {
				teamList.remove(this);
				nameTeam.remove(teamName, this);
			}
		}
	}
	
	public void clearTeam() {
		if(faketeam) {
			playerList.clear();
		} else {
			for (UUID key: playerTeam.keySet()) {
				Bukkit.getPlayer(key).setDisplayName(Bukkit.getPlayer(key).getName());
			}
			playerList.clear();
		}
	}
	
	public void removeTeam() {
		if(faketeam) {
			playerList.clear();
			teamList.remove(this);
		} else {
			for (UUID key: playerTeam.keySet()) {
				playerTeam.remove(key, this);
				Bukkit.getPlayer(key).setDisplayName(Bukkit.getPlayer(key).getName());
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

	@Override
	public void onPartyStart() {
		allPlayers.addAll(playerList);
	}
}