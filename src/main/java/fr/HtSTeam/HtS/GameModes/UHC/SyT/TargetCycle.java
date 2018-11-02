package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Team.Team;
import fr.HtSTeam.HtS.Utils.Lang;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class TargetCycle extends Option<Integer> {

	private boolean request = false;
	private Player p;
	
	public TargetCycle() {
		super(Material.CLOCK, Lang.get("option.targetcycle.name"), "20 minutes", 20, GUIRegister.syt);
	}

	@Override
	public void event(Player p) {
		request = true;
		p.closeInventory();
//		this.p = Bukkit.getPlayer(p.getUUID());
		p.sendMessage("§2Veuillez entrer la minute d'annonce des cibles.");		
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request/*&& e.getPlayer().equals(p)*/) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 0 && value <= 60) {
					setState(value);
					p.sendMessage("§2Annonce à " + getValue() + " minutes." );
					getParent().update(this);
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
		getParent().update(this);
	}
		
	@Timer
	public void defineTargets() {
		List<PlayerSyT> cycle = new ArrayList<PlayerSyT>();
		HashMap<Team, ArrayList<Player>> teamPlayers = new HashMap<Team, ArrayList<Player>>();
		for (Team team : Team.teamList)
			teamPlayers.put(team, team.getTeamPlayers());

		Team previousTeam = null;
		while(!teamPlayers.isEmpty()) {
			ArrayList<Team> teams = new ArrayList<Team>(teamPlayers.keySet());
			while (teams.get(0) == previousTeam)
				Collections.shuffle(teams);
			previousTeam = teams.get(0);
			
			ArrayList<PlayerSyT> sub_cycle = new ArrayList<PlayerSyT>();
			for (Team team : teams) {
				ArrayList<Player> players = teamPlayers.get(team);
				PlayerSyT player = (PlayerSyT) players.get(Randomizer.randI(0, players.size() - 1)); 
				sub_cycle.add(player); 
				players.remove(player);
				teamPlayers.put(team, players);
				if (players.isEmpty())
					teamPlayers.remove(team);
			}
			
			while(!cycle.isEmpty() && sub_cycle.get(0).getTeam() == cycle.get(cycle.size() - 1).getTeam())
				Collections.shuffle(sub_cycle);
			cycle.addAll(sub_cycle);
		}
		
		for (int i = 0; i < cycle.size(); i++)
			if (i == cycle.size() - 1)
				cycle.get(i).setTarget(cycle.get(0));
			else
				cycle.get(i).setTarget(cycle.get(i));
		
		Player.forEach(player -> {
			if (player.isInGame())
				player.sendMessage(Lang.get("option.targetcycle.displaytarget").replaceAll(Lang.REPLACE, ((PlayerSyT) player).getTarget().getDisplayName()));
		});
//		TargetsStatOption.init();
		
		String cycleT = "";
		for (PlayerSyT player : cycle)
			cycleT += "->" + player.getName();
		Main.LOGGER.logInfo(cycleT);
	}
	
	@Override
	public String getDescription() {
		return "§2[Aide]§r L'annonce des cibles se fera au bout de " + getValue() + " minutes de jeu.";
	}
}