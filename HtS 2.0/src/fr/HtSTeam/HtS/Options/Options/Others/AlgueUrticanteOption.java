package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class AlgueUrticanteOption extends OptionBuilder implements Alterable {
	
	private boolean activate = false;
	
	public AlgueUrticanteOption() {
		super(Material.VINE, "Algue Urticante", "§4Désactivé", "Désactivé", OptionRegister.other);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		setState(activate);
	}
	
	@Override
	public void setState(boolean value) {
		activate = value;
		if (value) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
		}
		parent.update(this);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onAlgaeCatch(org.bukkit.event.player.PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().getType() == Material.DOUBLE_PLANT && activate) {
			e.setCancelled(true);
			e.getPlayer().setHealth(e.getPlayer().getHealth()-1);
			e.getItem().remove();
		}
	}
}
