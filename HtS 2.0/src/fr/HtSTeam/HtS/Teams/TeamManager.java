package fr.HtSTeam.HtS.Teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import fr.HtSTeam.HtS.Main;

public class TeamManager {
		
	public static ArrayList<TeamManager> teamList = new ArrayList<TeamManager>();
	public static Map<String, TeamManager> nameTeam = new HashMap<String, TeamManager>();
	public static Map<Player, TeamManager> playerTeam = new HashMap<Player, TeamManager>();
	
	private Team team;
	
	private String teamName;
	private String teamColor;
	private byte teamByte;
	private boolean faketeam = false;
	
	private ArrayList<Player> playerList = new ArrayList<Player>();
	
	public TeamManager(String teamName, String teamColor) {
		teamList.add(this);
		nameTeam.put(teamName, this);
		
		this.teamName = teamName;
		this.teamColor = teamColor;
		teamByte = getWoolByte(teamColor);
		
		team = Main.b.registerNewTeam(teamName);
		team.setColor(ChatColor.valueOf(teamColor.toUpperCase()));
		team.setPrefix("[" + ChatColor.valueOf(teamColor.toUpperCase()) + teamName + "§r] ");
	}
	
	public void addPlayer(Player p) {
		if(faketeam) {
			playerList.add(p);
		} else {
			playerList.add(p);
			playerTeam.put(p, this);
			team.addEntry(p.getName());
			p.setScoreboard(Main.b);
		}
	}
	
	public void removePlayer(Player p) {
		if(faketeam) {
			playerList.remove(p);
			if(playerList.size() == 0) {
				teamList.remove(this);
			}
		} else {
			playerList.remove(p);
			playerTeam.remove(p, this);
			team.removeEntry(p.getName());
			if(getTeamSize() == 0)
				teamList.remove(this);
		}
	}
	
	public void clearTeam() {
		if(faketeam) {
			playerList.clear();
			teamList.remove(this);
		} else {
			for (String entry : team.getEntries()) {
				playerTeam.remove(Bukkit.getPlayer(entry), this);
				team.removeEntry(entry);
			}
			playerList.clear();
			teamList.remove(this);
		}
	}
	
	public void setTeamFriendlyFire(boolean friendlyfire) {
		team.setAllowFriendlyFire(friendlyfire);
	}
	
	public void setTeamSeeInvisible(boolean invisible) {
		team.setCanSeeFriendlyInvisibles(invisible);
	}
	
	public void setTeamNameTag(OptionStatus visible) {
		team.setOption(Option.NAME_TAG_VISIBILITY, visible);
	}
	
	public void setTeamCollision(OptionStatus collision) {
		team.setOption(Option.COLLISION_RULE, collision);
	}
	
	public void setTeamDeathMessage(OptionStatus deathmsg) {
		team.setOption(Option.DEATH_MESSAGE_VISIBILITY, deathmsg);
	}
	
	public String getTeamName() { return teamName; }
	public String getTeamColor() { return teamColor; }
	public byte getTeamByte() { return teamByte; }
	public int getTeamSize() { return team.getSize(); }
	
	public Scoreboard getScoreboard() { return team.getScoreboard(); }
	
	public ArrayList<Player> getTeamPlayers() { return playerList; }
	
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