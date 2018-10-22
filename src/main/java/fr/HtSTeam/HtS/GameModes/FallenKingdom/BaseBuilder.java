package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Teams.Team;

public class BaseBuilder {
	
	public static ArrayList<BaseBuilder> baseList = new ArrayList<BaseBuilder>();
	public static Map<String, BaseBuilder> nameBase = new HashMap<String, BaseBuilder>();
	public static Map<UUID, BaseBuilder> playerBase = new HashMap<UUID, BaseBuilder>();
	
	private Team team;
	private String baseName;
	private Block pos1, pos2;
	private boolean neutral = false;
	
	private ArrayList<UUID> playerList = new ArrayList<UUID>();
	
	
	/**
	 * Constructeur d'une base
	 * @param baseName nom de la base
	 * @param pos1 premier angle de la base
	 * @param pos2 second angle de la base
	 * 
	 */
	public BaseBuilder(String baseName, Block pos1, Block pos2) {
		if (nameBase.containsKey(baseName))
			return;		
		baseList.add(this);
		nameBase.put(baseName, this);
		this.baseName = baseName;
		this.pos1 = pos1;
		this.pos2 = pos2;
	}
	
	/*Permet d'ajouter un joueur à la protection de la base
	 * @param p Joueur à ajouter
	 */
	public void addPlayer(Player p) {
		if(neutral)
			return;
		playerList.add(p.getUniqueId());
		playerBase.put(p.getUniqueId(), this);
	}
	
	
	/** Permet de supprimer un joueur de la base
	 * @param p Joueur à retirer
	 */
	public void removePlayer(Player p) {
		if(neutral)
			return;
		playerList.remove(p.getUniqueId());
		playerBase.remove(p.getUniqueId(), this);
		if (playerList.size() == 0) {
			baseList.remove(this);
			nameBase.remove(baseName, this);
		}
	}
	
	/** Permet de supprimer la base
	 */
	public void deleteBase() {
		if(!neutral) {
			for (UUID uuid : playerList)
				playerBase.remove(Bukkit.getPlayer(uuid), this);
			playerList.clear();
		}
		baseList.remove(this);
		nameBase.remove(baseName, this);
	}
	
	/**Permet de définir la team lié à la base
	 * @param team La team à qui appartient la base
	 */
	public void setTeam(Team team) {
		if(neutral)
			return;
		this.team = team;
//		playerList = team.getTeamPlayers();
	}
	
	/**
	 * @return Le nom de la base
	 */
	public String getBaseName() { return baseName; }
	/**@return Base neutre ou non 
	 */
	public boolean isNeutral() { return neutral; }
	
	/*
	 * @param Défini si la base est neutre ou non
	 */
	public void setNeutral(boolean neutral) { this.neutral = neutral; }
	
	/**
	 * @return Les X et Z des deux angles de la base
	 */
	public int[][] getPos() {
		int pos[][] = new int[2][2];
		pos[0][0] = pos1.getX();
		pos[1][0] = pos1.getZ();
		pos[0][1] = pos2.getX();
		pos[1][1] = pos2.getZ();
		return pos;
	}
	
	/**
	 * 
	 * @return Les X et Z des deux angles de la base triés par odre croissant
	 */
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

	/**
	 * 
	 * @return La team liée à la base
	 */
	public Team getTeam() {
		if(neutral)
			return null;
		return team;
	}
	
	/**
	 * Ajoute tout les joueurs de la team définie à la base
	 */
	public void addAllPlayers() {
		for(UUID uuid : playerList)
			playerBase.put(uuid, this);
	}
}
