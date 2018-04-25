package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
	public void definedTarget() {
		List<UUID> players = new ArrayList<UUID>();
		players.addAll(PlayerInGame.playerInGame);
		while(players.size() > 0) {
			Random r = new Random();
			int rand = r.nextInt(players.size());
			if(targetCycle.size() == 0) {
				targetCycle.add(players.get(rand));
				players.remove(rand);
			} else {	
				if(TeamBuilder.playerTeam.get(players.get(rand)).equals(TeamBuilder.playerTeam.get(players.get(rand - 1))))
				targetCycle.add(players.get(rand));
				players.remove(rand);
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
