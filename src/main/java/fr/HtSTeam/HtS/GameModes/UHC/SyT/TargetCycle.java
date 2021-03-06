package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.TargetsStatOption;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.Annotation.AwaitingPlayer;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Players.PlayerManager;
import fr.HtSTeam.HtS.Players.PlayerReconnection;
import fr.HtSTeam.HtS.Teams.TeamBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class TargetCycle extends OptionBuilder<Integer> {

	private boolean request = false;
	private Player p;
	public List<UUID> targetCycle = new ArrayList<UUID>();
	
	public TargetCycle() {
		super(Material.WATCH, "Annonce des cibles", "20 minutes", 20, GUIRegister.syt);
	}

	@Override
	public void event(Player p) {
		request = true;
		p.closeInventory();
		this.p = p;
		p.sendMessage("§2Veuillez entrer la minute d'annonce des cibles.");		
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 0 && value <= 60) {
					setState(value);
					p.sendMessage("§2Annonce à " + getValue() + " minutes." );
					parent.update(this);
					request = false;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 0 et 60.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
	
	@Override
	public void setState(Integer value) {
		setValue(value);
		this.getItemStack().setLore("§2" + value + " minutes");
		parent.update(this);
	}
		
	@Timer
	public void defineTargets() {
		List<UUID> cycle = new ArrayList<UUID>();
		HashMap<TeamBuilder, ArrayList<UUID>> teamPlayers = new HashMap<TeamBuilder, ArrayList<UUID>>();
		for (TeamBuilder tb : TeamBuilder.teamList)
			teamPlayers.put(tb, new ArrayList<UUID>(tb.getTeamPlayers()));

		TeamBuilder previousTeam = null;
		while(!teamPlayers.isEmpty()) {
			ArrayList<TeamBuilder> tbs = new ArrayList<TeamBuilder>(teamPlayers.keySet());
			while (tbs.get(0) == previousTeam)
				Collections.shuffle(tbs);
			previousTeam = tbs.get(0);
			
			ArrayList<UUID> sub_cycle = new ArrayList<UUID>();
			for (TeamBuilder tb : tbs) {
				ArrayList<UUID> players = teamPlayers.get(tb);
				UUID uuid = players.get(Randomizer.RandI(0, players.size() - 1)); 
				sub_cycle.add(uuid); 
				players.remove(uuid);
				teamPlayers.put(tb, players);
				if (players.isEmpty())
					teamPlayers.remove(tb);
			}
			
			while(!cycle.isEmpty() && TeamBuilder.playerTeam.get(sub_cycle.get(0)) == TeamBuilder.playerTeam.get(cycle.get(cycle.size() - 1)))
				Collections.shuffle(sub_cycle);
			cycle.addAll(sub_cycle);
		}
		
		targetCycle = cycle;
		
		displayTarget();
		TargetsStatOption.init();
		
		String cycleT = "";
		for (UUID uuid : targetCycle)
			cycleT += "->" + PlayerInGame.uuidToName.get(uuid);
		System.out.println(cycleT);
	}
	
	public void displayTarget() {
		targetCycle.forEach(uuid -> { if (PlayerManager.isConnected(uuid)) displayTarget(uuid); else PlayerReconnection.add(uuid, this); });
	}
	
	@AwaitingPlayer	
	public void displayTarget(UUID uuid) {
		if (targetCycle.indexOf(uuid) == targetCycle.size() - 1)
			Bukkit.getPlayer(uuid)
			.sendMessage("§4Votre cible est " + PlayerInGame.uuidToName.get(targetCycle.get(0)));
		else
			Bukkit.getPlayer(uuid)
			.sendMessage("§4Votre cible est " + PlayerInGame.uuidToName.get(targetCycle.get(targetCycle.indexOf(uuid) + 1)));
			
	}
	
	public UUID getTarget(Player p) {
		return getTarget(p.getUniqueId());
	}
	
	public UUID getTarget(UUID uuid) {
		if(targetCycle.size() == 1) return null;
		int index = targetCycle.indexOf(uuid);
		if(index < targetCycle.size() - 1)
			return targetCycle.get(index + 1);
		else 
			return targetCycle.get(0);
	}
	
	public UUID getHunter(Player p) {
		return getHunter(p.getUniqueId());
	}
	
	public UUID getHunter(UUID uuid) {
		if(targetCycle.size() == 1) return null;
		int index = targetCycle.indexOf(uuid);
		if(index > 0)
			return targetCycle.get(index - 1);
		else 
			return targetCycle.get(targetCycle.size() - 1);
	}

	@Override
	public String description() {
		return "§2[Aide]§r L'annonce des cibles se fera au bout de " + getValue() + " minutes de jeu.";
	}
}
