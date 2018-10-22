package fr.HtSTeam.HtS.Teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Player.Player;

public class Team implements StartTrigger {

	public static ArrayList<Team> teamList = new ArrayList<Team>();
	public static Map<String, Team> nameTeam = new HashMap<String, Team>();

	private String teamName;
	private String teamColor;
	private boolean alive;

	private boolean faketeam = false;

	private ArrayList<Player> playerList = new ArrayList<Player>();
	
	/**
	 * Creates a team
	 * 
	 * @param teamName
	 * @param teamColor
	 */
	public Team(String teamName, String teamColor) {
		if (nameTeam.containsKey(teamName))
			return;
		teamList.add(this);
		nameTeam.put(teamName, this);

		this.teamName = teamName;
		this.teamColor = teamColor;
		alive = false;
	}
	
	/**
	 * Returns team's name
	 * @return String
	 */
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * Returns the team's color (minecraft colors)
	 * @return String
	 */
	public String getTeamColor() {
		return teamColor;
	}
	
	/**
	 * Returns true if at least 1 player of this team is alive
	 * @return boolean
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Returns members' list
	 * @return ArrayList<Player>
	 */
	public ArrayList<Player> getTeamPlayers() {
		return playerList;
	}
	
	/**
	 * Returns number of members
	 * @return int
	 */
	public int getTeamSize() {
		return playerList.size();
	}
	
	/**
	 * Returns true if the team is fake
	 * @return boolean
	 */
	public boolean isFakeTeam() {
		return faketeam;
	}
	
	/**
	 * Turns the team into a fake team, ie., won't have impact on victory detection
	 * @param faketeam
	 */
	public void setFakeTeam(boolean faketeam) {
		this.faketeam = faketeam;
	}
	
	/**	
	 * Adds this player to the team
	 * @param player
	 */
	public void add(Player player) {
		playerList.add(player);
	}
	
	/**
	 * Removes this player from the team
	 * @param player
	 */
	public void remove(Player player) {
		playerList.remove(player);
		if (playerList.isEmpty())
			alive = false;
	}
	
	/**
	 * Removes all players from the team
	 */
	public void clear() {
		if (faketeam)
			playerList.forEach(player -> player.setFakeTeam(null));
		else
			playerList.forEach(player -> player.setTeam(null));
	}
	
	/**
	 *  Deletes the team
	 */
	public void delete() {
		if (faketeam)
			playerList.forEach(player -> player.setFakeTeam(null));
		else
			playerList.forEach(player -> player.setTeam(null));

		teamList.remove(this);
		nameTeam.remove(teamName);
	}

	@Override
	public void onPartyStart() {
		teamList.forEach(team -> { if (!team.playerList.isEmpty()) team.alive = true; });
	}
}