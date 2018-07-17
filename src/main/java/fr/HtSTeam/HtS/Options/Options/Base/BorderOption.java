package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class BorderOption extends OptionBuilder<Integer> {
	
	
	private boolean request = false;
	private Player p;
	private WorldBorder border = Main.world.getWorldBorder();
	
	public BorderOption() {
		super(Material.IRON_FENCE, "Taille de la bordure", "§d1000 * 1000", 1000, GUIRegister.base);
		border.setCenter(0.0, 0.0);
		border.setSize(1000);
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
					setState(value);
					p.sendMessage("§2Bordure à " + value + " blocs du centre." );				
					parent.update(this);
					request = false;
					
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 500 et 2500.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}

	@Override
	public void setState(Integer value) {
		setValue(value * 2);	
		this.getItemStack().setLore("§d" + value * 2 + " * " + value * 2);
		border.setSize(value * 2);
	}
	


}
