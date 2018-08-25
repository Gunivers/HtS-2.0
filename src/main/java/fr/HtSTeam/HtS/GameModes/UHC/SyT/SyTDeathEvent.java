package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Players.PlayerRemove;
import fr.HtSTeam.HtS.Players.PlayerInGame;

public class SyTDeathEvent implements Listener, PlayerRemove {
	
	private String broadcast;
	
	{
		addToList();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onKillSomeone(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		Player eventVictim = e.getEntity();
		if (e.getEntity().getKiller() instanceof Player && !SyT.targetCycleOption.targetCycle.isEmpty()) {
			Player eventKiller = e.getEntity().getKiller();
			UUID killerHunter = SyT.targetCycleOption.getHunter(eventKiller);
			UUID killerTarget = SyT.targetCycleOption.getTarget(eventKiller);
			UUID victimHunter = SyT.targetCycleOption.getHunter(eventVictim);
			UUID victimTarget = SyT.targetCycleOption.getTarget(eventVictim);
			
			
			// Kill victim
			if (killerTarget.equals(eventVictim.getUniqueId())) {
				eventKiller.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0));
				eventKiller.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));

				if (eventKiller.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < 24) {
					eventKiller.getAttribute(Attribute.GENERIC_MAX_HEALTH)
							.setBaseValue(eventKiller.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);
					eventKiller.setHealth(eventKiller.getHealth() + 2);
				}
				broadcast = eventVictim.getName() + " a été assassiné.";
				if (EnumState.getState().equals(EnumState.RUNNING))
					eventKiller.sendMessage("§2Cible éliminée. Nouvelle cible : "+ PlayerInGame.uuidToName.get(victimTarget));

				// Kill hunter
			} else if (killerHunter.equals(eventVictim.getUniqueId())) {
				eventKiller.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0));
				if (eventKiller.getHealth() >= eventKiller.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 4) {
					eventKiller.setHealth(eventKiller.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				} else {
					eventKiller.setHealth(eventKiller.getHealth() + 4);
				}
				broadcast = eventVictim.getName() + " a échoué dans sa mission.";
				if (EnumState.getState().equals(EnumState.RUNNING)) {
					Bukkit.getPlayer(victimHunter).sendMessage("§2Cible éliminée. Nouvelle cible : "+ PlayerInGame.uuidToName.get(victimTarget));
					eventKiller.sendMessage("§6Celui-ci semblait vous vouloir du mal, il est fort probable qu'il cherchait à vous éliminer.");
				}
				// Kill other people
			} else {
				eventKiller.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
				eventKiller.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 6000, 0));

				if (eventKiller.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() > 16) {
					eventKiller.getAttribute(Attribute.GENERIC_MAX_HEALTH)
							.setBaseValue(eventKiller.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2);
				}

				broadcast = eventVictim.getName() + " est mort.";
				if (EnumState.getState().equals(EnumState.RUNNING)) {
					Bukkit.getPlayer(victimHunter).sendMessage("§2Votre cible a été tuée, une nouvelle cible vous est attribuée : "+ PlayerInGame.uuidToName.get(victimTarget));
					eventKiller.sendMessage("§4Que faites-vous ?! Ce n'était pas la cible qui vous était attribuée !");
					eventKiller.sendMessage("§7§oVous avez du remord...");
				}
			}
			SyT.targetCycleOption.targetCycle.remove(eventVictim.getUniqueId());
		} else {
			broadcast = eventVictim.getName() + " est mort.";
			removePlayer(eventVictim.getUniqueId(), eventVictim.getName());
		}
		Bukkit.broadcastMessage(broadcast);
	}
	
	public void removePlayer(UUID uuid, String name) {	
		Bukkit.getPlayer(SyT.targetCycleOption.getHunter(uuid))
				.sendMessage("§2Votre cible a été tuée, une nouvelle cible vous est attribuée : "
						+ PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid)));
		SyT.targetCycleOption.targetCycle.remove(uuid);	
	}

}
