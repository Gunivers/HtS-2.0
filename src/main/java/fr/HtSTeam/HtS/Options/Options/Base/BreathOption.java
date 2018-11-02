package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.ChatNumberOption;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;
import fr.HtSTeam.HtS.Utils.PRIORITY;

public class BreathOption extends ChatNumberOption implements StartTrigger {
	
	private boolean activate = false;
	private boolean alert = false;
	
	public BreathOption() {
		super(Material.GUNPOWDER, "Souffle des profondeurs", "§4Désactivé", -1, GUIRegister.base, 0, 120);
	}
	
	@Override
	public void dispValidMessage() {
		if(getValue() > 0)
			p.sendMessage("§2Le souffle des profondeurs s'activera à " + getValue() + " minutes." );
		else {
			setValue(-1);
			p.sendMessage("§2Le souffle des profondeurs a été désactivé.");	
		}
	}
	
	@Override
	public void setState(Integer value) {
		if(value != -1) {
			setValue(value);
			getItemStack().setItem(Material.GLOWSTONE_DUST);
			getItemStack().setLore("§d" + value + " minutes");
		} else {
			getItemStack().setItem(Material.GUNPOWDER);
			getItemStack().setLore("§4Désactivé");
		}
		getParent().update(this);
	}
	
	@EventHandler
	public void playerHeight(PlayerMoveEvent e) {
		if(activate && getItemStack().getType().equals(Material.GLOWSTONE_DUST)) {
			if(e.getPlayer().getLocation().getY() <= 36 && e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000 * 20, 0, false, false));
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10000 * 20, 0, false, false));
			} else if (e.getPlayer().getLocation().getY() > 36 && e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
				e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
				e.getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
				}
		}
	}
	
	
	@Timer(PRIORITY.HIGHEST)
	public void alert() {
		if(getValue().equals(-1) || alert) return;
		setValue(getValue() + 1);
		alert = true;
		Bukkit.broadcastMessage("§4Les mineurs ont miné beaucoup trop profondement et ont ouvert des poches de soufre. Le soufre envahira les mines dans 1 minutes.");
	}
	
	@Timer(PRIORITY.LOWEST)
	public void run() {
		activate = true;
		Bukkit.broadcastMessage("§4Le soufre a envahi les mines.");
	}

	@Override
	public void onPartyStart() {
		if(getValue().equals(-1)) return;
		setValue(getValue() - 1);
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r Le souffle de profondeurs donnent des effets négatifs aux joueurs se trouvant sous la couche 36 de l'overworld limitant ainsi le temps de minage.\r Ce dernier s'active à " + getValue() + " minutes.";
	}

	@Override
	public void dispRequestMessage() {
		p.sendMessage("Veuillez saisir la minute d'activation du souffle des profondeurs.");
	}
}
