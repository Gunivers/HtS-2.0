package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.Annotation.PRIORITY;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public class BreathOption extends OptionBuilder<Integer> implements StartTrigger {
	
	private boolean request = false; 
	private Player p;
	private boolean activate = false;
	private boolean alert = false;
	
	public BreathOption() {
		super(Material.SULPHUR, "Souffle des profondeurs", "§4Désactivé", -1, GUIRegister.base);
	}

	@Override
	public void event(Player p) {
		request = true;
		p.closeInventory();
		this.p = p;
		p.sendMessage("§2Veuillez entrer la minute d'activation du souffle des profondeurs (0 : désactivé).");
		
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 0 && value <= 120) {
					if(value > 0) {
						setState(value);
						p.sendMessage("§2Le souffle des profondeurs s'activera à " + value + " minutes." );
						
					} else {
						setState(-1);
						p.sendMessage("§2Le souffle des profondeurs a été désactivé.");	
					}
					parent.update(this);
					request = false;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 0 et 120.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
	
	@Override
	public void setState(Integer value) {
		if(value != -1) {
			setValue(value);
			getItemStack().setItem(Material.GLOWSTONE_DUST, (short) 0);
			getItemStack().setLore("§d" + value + " minutes");
		} else {
			getItemStack().setItem(Material.SULPHUR, (short) 0);
			getItemStack().setLore("§4Désactivé");
		}
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
}
