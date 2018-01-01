package fr.HtSTeam.HtS.Options.Options.Modifier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class AlgueUrticanteOption extends OptionsManager implements Listener {
	
	private boolean activate = false;
	
	public AlgueUrticanteOption() {
		super(Material.VINE, "Algue Urticante", "§4Désactivé", "Désactivé", OptionsRegister.modifiers);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		if (activate) {
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
