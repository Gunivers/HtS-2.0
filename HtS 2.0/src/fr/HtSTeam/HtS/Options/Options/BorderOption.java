package fr.HtSTeam.HtS.Options.Options;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class BorderOption extends OptionsManager {
	
	
	public static int borderSize = 1000;
	private boolean request = false;
	private Player p;
	
	public BorderOption() {
		super(Material.IRON_FENCE, "Taille de la bordure", "1000 * 1000", "1000", OptionsRegister.uhc);
	}

	@Override
	public void event(Player p) {
		request = true;
		p.closeInventory();
		this.p = p;
		p.sendMessage("§2Veuillez entrer la distance de la bordure par rapport au 0;0.");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 500 && value <= 2500) {
					borderSize = value * 2;
					p.sendMessage("§2Bordure à " + value + " blocs du centre." );
					this.getItemStackManager().setLore(value * 2 + " * " + value * 2);
					parent.update(this);
					request = true;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 500 et 2500.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
	


}
