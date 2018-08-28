package fr.HtSTeam.HtS.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import fr.HtSTeam.HtS.Teams.TeamBuilder;

public class Player {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static HashMap<UUID, Player> uuids = new HashMap<UUID, Player>();
	
	public static ArrayList<Player> getPlayers() { return players; }
	public static Player getPlayerFromUUID(UUID uuid) { return uuids.get(uuid); }
	
	private UUID uuid;
	private String name;
	private String display_name;

	private TeamBuilder team;
	private TeamBuilder fake_team;
	
	private UUID target;
	private UUID hunter;
	
	private Player(org.bukkit.entity.Player p) {
		players.add(this);
		uuids.put(p.getUniqueId(), this);
		
		uuid = p.getUniqueId();
		name = p.getName();
		display_name = p.getDisplayName();
	}
	
	public static Player instance(org.bukkit.entity.Player p) {
		if (uuids.containsKey(p.getUniqueId()))
			return uuids.get(p.getUniqueId());
		return new Player(p);	
	}
	
	public UUID getUUID() { return uuid; }
	public String getName() { return name; }
	public String getDisplay() { return display_name; }
	
	public TeamBuilder getTeam() { return team; }
	public TeamBuilder getFakeTeam() { return fake_team; }
	
	public UUID getTarget() { return target; }
	public UUID getHunter() { return hunter; }
}