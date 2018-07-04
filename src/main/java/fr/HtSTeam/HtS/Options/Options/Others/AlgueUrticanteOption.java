package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class AlgueUrticanteOption extends OptionBuilder<Boolean> implements Alterable {
		
	public AlgueUrticanteOption() {
		super(Material.VINE, "Algue Urticante", "§4Désactivé", false, OptionRegister.other);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}
	
	@Override
	public void setState(boolean value) {
		if (value)
			getItemStack().setLore("§2Activé");
		else
			getItemStack().setLore("§4Désactivé");
		setValue(value);
		parent.update(this);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onAlgaeCatch(org.bukkit.event.player.PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().getType() == Material.DOUBLE_PLANT && getValue()) {
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
}
