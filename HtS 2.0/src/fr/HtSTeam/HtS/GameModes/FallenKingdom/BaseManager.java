package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Teams.TeamManager;

public class BaseManager {
	
	public static ArrayList<BaseManager> baseList = new ArrayList<BaseManager>();
	public static Map<String, BaseManager> nameBase = new HashMap<String, BaseManager>();
	public static Map<UUID, BaseManager> playerBase = new HashMap<UUID, BaseManager>();
	
	private TeamManager team;
	private String baseName;
	private Block pos1, pos2;
	private boolean neutral = false;
	
	private ArrayList<UUID> playerList = new ArrayList<UUID>();
	
	public BaseManager(String baseName, Block pos1, Block pos2) {
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
	
	public void addTeam(TeamManager team) {
		if(neutral)
			return;
		this.team = team;
		playerList = team.getTeamPlayers();
		for(UUID uuid : playerList)
			playerBase.put(uuid, this);
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

	public TeamManager getTeam() {
		if(neutral)
			return null;
		return team;
	}
}
