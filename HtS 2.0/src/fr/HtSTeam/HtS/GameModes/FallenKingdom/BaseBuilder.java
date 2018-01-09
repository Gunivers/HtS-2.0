package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Teams.TeamBuilder;

public class BaseBuilder {
	
	public static ArrayList<BaseBuilder> baseList = new ArrayList<BaseBuilder>();
	public static Map<String, BaseBuilder> nameBase = new HashMap<String, BaseBuilder>();
	public static Map<UUID, BaseBuilder> playerBase = new HashMap<UUID, BaseBuilder>();
	
	private TeamBuilder team;
	private String baseName;
	private Block pos1, pos2;
	private boolean neutral = false;
	
	private ArrayList<UUID> playerList = new ArrayList<UUID>();
	
	public BaseBuilder(String baseName, Block pos1, Block pos2) {
		if (nameBase.containsKey(baseName))
			return;		
		baseList.add(this);
		nameBase.put(baseName, this);
		this.baseName = baseName;
		this.pos1 = pos1;
		this.pos2 = pos2;
	}
	
	public void addPlayer(Player p) {
		if(neutral)
			return;
		playerList.add(p.getUniqueId());
		playerBase.put(p.getUniqueId(), this);
	}
	
	public void removePlayer(Player p) {
		if(neutral)
			return;
		playerList.remove(p.getUniqueId());
		playerBase.remove(p, this);
		if (playerList.size() == 0) {
			baseList.remove(this);
			nameBase.remove(baseName, this);
		}
	}
	
	public void deleteBase() {
		if(!neutral) {
			for (UUID uuid : playerList)
				playerBase.remove(Bukkit.getPlayer(uuid), this);
			playerList.clear();
		}
		baseList.remove(this);
		nameBase.remove(baseName, this);
	}
	
	public void addTeam(TeamBuilder team) {
		if(neutral)
			return;
		this.team = team;
		playerList = team.getTeamPlayers();
	}
	
	public String getBaseName() { return baseName; }
	public boolean isNeutral() { return neutral; }
	public void setNeutral(boolean neutral) { this.neutral = neutral; }
	
	public int[][] getPos() {
		int pos[][] = new int[2][2];
		pos[0][0] = pos1.getX();
		pos[1][0] = pos1.getZ();
		pos[0][1] = pos2.getX();
		pos[1][1] = pos2.getZ();
		return pos;
	}
	
	public int[][] getOrderPos() {
		int pos[][] = new int[2][2];
		pos[0][0] = pos1.getX();
		pos[0][1] = pos2.getX();
		pos[1][0] = pos1.getZ();
		pos[1][1] = pos2.getZ();
		for(int[] tab : pos)
    		Arrays.sort(tab);
		return pos;
	}

	public TeamBuilder getTeam() {
		if(neutral)
			return null;
		return team;
	}
	
	public void addAllPlayers() {
		for(UUID uuid : playerList)
			playerBase.put(uuid, this);
	}
}
