package fr.HtSTeam.HtS.Options.Options.UHC;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Options.Structure.Timer;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public class BreathOption extends OptionsManager implements StartTrigger {
	
	private boolean request = false; 
	private Player p;
	private boolean activate = false;
	
	public BreathOption() {
		super(Material.SULPHUR, "Souffle des profondeurs", "§4Désactivé", "Désactivé", OptionsRegister.uhc);
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
						setValue(Integer.toString(value));
						p.sendMessage("§2Le souffle des profondeurs s'activera à " + value + " minutes." );
						this.getItemStackManager().setLore("§d" + value + " minutes");
						getItemStackManager().setItem(Material.GLOWSTONE_DUST, (short) 0);
					} else {
						setValue("Désactivé");
						p.sendMessage("§2Le souffle des profondeurs a été désactivé.");
						this.getItemStackManager().setLore("§4Désactivé");
						getItemStackManager().setItem(Material.SULPHUR, (short) 0);
					}
					parent.update(this);
					request = true;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 0 et 120.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
	
	@EventHandler
	public void playerHeight(PlayerMoveEvent e) {
		if(activate && getItemStackManager().getMaterial().equals(Material.GLOWSTONE_DUST)) {
			if(e.getPlayer().getLocation().getY() <= 36 && e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000 * 20, 0, false, false));
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10000 * 20, 0, false, false));
			} else if (e.getPlayer().getLocation().getY() > 36 && e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
				e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
				e.getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
				}
		}
	}
	
	@Timer
	private void active() {
		activate = true;
		Bukkit.broadcastMessage("§4Le souffre a envahis les mines.");
	}
	
	@Timer
	private void alert() {
		setValue(Integer.toString(Integer.parseInt(getValue()) + 1));
		Bukkit.broadcastMessage("§4Le souffre a envahis les mines.");
	}

	@Override
	public void onPartyStart() {
		setValue(Integer.toString(Integer.parseInt(getValue()) - 1));
	}
}
