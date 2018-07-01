package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class RadarOption extends OptionBuilder {
	
	private boolean request = false;
	private Player p;

	public RadarOption() {
		super(Material.WATCH, "Lancement du Radar", "20 minutes", "20", OptionRegister.syt);
	}

	@Override
	public void event(Player p) {
		this.p = p;
		p.closeInventory();
		request = true;
		p.sendMessage("§2Veuillez saisir le délais d'activation du Radar.");		
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if (value >= 0 && value <= 60) {
					setValue(Integer.toString(value));
					p.sendMessage("§2Radar à " + getValue() + " minutes.");
					this.getItemStack().setLore("§2" + value + " minutes");
					parent.update(this);
					request = false;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 0 et 60.");
			} catch (NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
}
