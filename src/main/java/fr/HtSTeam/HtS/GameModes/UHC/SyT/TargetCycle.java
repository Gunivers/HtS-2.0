package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Teams.TeamBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class TargetCycle extends OptionBuilder {

	private boolean request = false;
	private Player p;
	public List<UUID> targetCycle = new ArrayList<UUID>();
	
	public TargetCycle() {
		super(Material.WATCH, "Annonce des cibles", "20 minutes", "20", OptionRegister.syt);
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
					setValue(Integer.toString(value));
					p.sendMessage("§2Annonce à " + getValue() + " minutes." );
					this.getItemStack().setLore("§2" + value + " minutes");
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
		
	@Timer
	public void defineTargets() {
		List<UUID> cycle = new ArrayList<UUID>();
		HashMap<TeamBuilder, ArrayList<UUID>> teamPlayers = new HashMap<TeamBuilder, ArrayList<UUID>>();
		for(TeamBuilder tb : TeamBuilder.teamList)
			teamPlayers.put(tb, new ArrayList<UUID>(tb.getTeamPlayers()));
		TeamBuilder firstPlayerTeam = null;
		while (teamPlayers.size() > 0) {
			List<TeamBuilder> teams = new ArrayList<TeamBuilder>();
			teams.addAll(TeamBuilder.teamList);
			int i = 0;
			while (teams.size() > 0) {
				TeamBuilder randTeam = teams.get(Randomizer.Rand(teams.size()));
				if (i == teamPlayers.size() - 1 && randTeam.equals(firstPlayerTeam)) {
					UUID lastPlayer = cycle.remove(cycle.size() - 1);
					cycle.add(teamPlayers.get(randTeam).remove(Randomizer.Rand(teamPlayers.get(randTeam).size())));
					cycle.add(lastPlayer);
				} else
					cycle.add(teamPlayers.get(randTeam).remove(Randomizer.Rand(teamPlayers.get(randTeam).size())));
				if (firstPlayerTeam == null && i == 0) {
					firstPlayerTeam = randTeam;
				}
				i++;
				if (teamPlayers.get(randTeam).size() == 0)
					teamPlayers.remove(randTeam);
				teams.remove(randTeam);
			}
		}
		targetCycle = cycle;
		displayTarget();
		for (UUID uuid : targetCycle) {
			System.out.println(Bukkit.getPlayer(uuid).getName() + " : "
					+ Bukkit.getPlayer(getTarget(Bukkit.getPlayer(uuid))).getName());
		}
	}

	public void displayTarget() {
		for(int i = 0; i < targetCycle.size(); i++) {
			if(i != targetCycle.size() - 1) {
				Bukkit.getPlayer(targetCycle.get(i)).sendMessage("§4Votre cible est " + Bukkit.getPlayer(targetCycle.get(i + 1)).getName());
			} else {
				Bukkit.getPlayer(targetCycle.get(i)).sendMessage("§4Votre cible est " + Bukkit.getPlayer(targetCycle.get(0)).getName());
			}
		}
	}
	
	public UUID getTarget(Player p) {
		int index = targetCycle.indexOf(p.getUniqueId());
		if(index < targetCycle.size() - 1)
			return targetCycle.get(index + 1);
		else 
			return targetCycle.get(0);
	}
	
	public UUID getHunter(Player p) {
		int index = targetCycle.indexOf(p.getUniqueId());
		if(index > 0)
			return targetCycle.get(index - 1);
		else 
			return targetCycle.get(targetCycle.size() - 1);
	}
	
	public void giveTarget() {
		for(UUID uuid : PlayerInGame.playerInGame)
			SyT.targetCycleOption.getTarget(Bukkit.getPlayer(uuid));
	}

}
