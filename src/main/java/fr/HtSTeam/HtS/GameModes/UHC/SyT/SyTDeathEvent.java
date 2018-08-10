package fr.HtSTeam.HtS.GameModes.UHC.SyT;

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

public class SyTDeathEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onKillSomeone(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		Player victim = e.getEntity();
		String broadcast;
		if (e.getEntity().getKiller() instanceof Player && !SyT.targetCycleOption.targetCycle.isEmpty()) {
			Player killer = e.getEntity().getKiller();

			// Kill victim
			if (SyT.targetCycleOption.getTarget(killer).equals(victim.getUniqueId())) {
				killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0));
				killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));

				if (killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < 24) {
					killer.getAttribute(Attribute.GENERIC_MAX_HEALTH)
							.setBaseValue(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);
					killer.setHealth(killer.getHealth() + 2);
				}
				SyT.targetCycleOption.targetCycle.remove(victim.getUniqueId());
				broadcast = victim.getName() + " a été tué par son chasseur.";
				if (EnumState.getState().equals(EnumState.RUNNING))
					killer.sendMessage("§2Cible éliminée. Nouvelle cible : "+ Bukkit.getPlayer(SyT.targetCycleOption.getTarget(killer)).getName());

				// Kill hunter
			} else if (SyT.targetCycleOption.getHunter(killer).equals(victim.getUniqueId())) {
				killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0));
				if (killer.getHealth() >= killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 4) {
					killer.setHealth(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				} else {
					killer.setHealth(killer.getHealth() + 4);
				}
				broadcast = victim.getName() + " a été tué par sa cible.";
				SyT.targetCycleOption.targetCycle.remove(victim.getUniqueId());
				if (EnumState.getState().equals(EnumState.RUNNING)) {
					Bukkit.getPlayer(SyT.targetCycleOption.getHunter(killer)).sendMessage("§2Cible éliminée. Nouvelle cible : "+ killer.getName());
					killer.sendMessage("§6Celui-ci semblait vous vouloir du mal, il est fort probable qu'il cherchait à vous éliminer.");
				}
				// Kill other people
			} else {
				killer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
				killer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 6000, 0));

				if (killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() > 16) {
					killer.getAttribute(Attribute.GENERIC_MAX_HEALTH)
							.setBaseValue(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2);
				}

				broadcast = victim.getName() + " est mort.";
				SyT.targetCycleOption.targetCycle.remove(victim.getUniqueId());
				if (EnumState.getState().equals(EnumState.RUNNING)) {
					Bukkit.getPlayer(SyT.targetCycleOption.getHunter(victim)).sendMessage("§2Votre cible a été tuée, une nouvelle cible vous est attribuée : "+ Bukkit.getPlayer(SyT.targetCycleOption.getTarget(victim)).getName());
					killer.sendMessage("§4Que faites-vous ?! Ce n'était pas la cible qui vous était attribuée !");
					killer.sendMessage("§7§oVous avez du remord...");
				}
			}
		} else {
			broadcast = victim.getName() + " est mort.";
			SyT.targetCycleOption.targetCycle.remove(victim.getUniqueId());
			Bukkit.getPlayer(SyT.targetCycleOption.getHunter(victim))
					.sendMessage("§2Votre cible a été tuée, une nouvelle cible vous est attribuée : "
							+ Bukkit.getPlayer(SyT.targetCycleOption.getTarget(victim)).getName());
		}
		Bukkit.broadcastMessage(broadcast);
	}

}
