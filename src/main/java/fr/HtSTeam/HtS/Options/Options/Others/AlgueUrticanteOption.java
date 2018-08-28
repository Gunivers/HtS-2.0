package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class AlgueUrticanteOption extends OptionBuilder<Boolean> {
		
	public AlgueUrticanteOption() {
		super(Material.VINE, "Algue Urticante", "§4Désactivé", false, GUIRegister.other, false);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}
	
	@Override
	public void setState(Boolean value) {
		if (value)
			getItemStack().setLore("§2Activé");
		else
			getItemStack().setLore("§4Désactivé");
		setValue(value);
		parent.update(this);
	}
	
	@EventHandler
	public void onAlgaeCatch(org.bukkit.event.player.PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().getType() == Material.TALL_GRASS && getValue()) {
			e.setCancelled(true);
			e.getPlayer().setHealth(e.getPlayer().getHealth()-1);
			e.getItem().remove();
		}
	}
	
	public boolean isActivated() {
		if (getValue() == true)
			return true;
		else
			return false;
	}

	@Override
	public String description() {
		return null;
	}
}
