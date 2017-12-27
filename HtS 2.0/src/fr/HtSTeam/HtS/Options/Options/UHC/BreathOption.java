package fr.HtSTeam.HtS.Options.Options.UHC;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class BreathOption extends OptionsManager {
	
	private boolean request = false; 
	private Player p;

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

}
