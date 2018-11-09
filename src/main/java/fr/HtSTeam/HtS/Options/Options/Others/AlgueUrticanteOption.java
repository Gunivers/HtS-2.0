package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class AlgueUrticanteOption extends Option<Boolean> {
		
	public AlgueUrticanteOption() {
		super(Material.VINE, "Algue Urticante", "§4Désactivé", false, GUIRegister.other);
	}

	@Override
	public void event(Player p) {
		setState(!value);
	}
	
	@Override
	public void setState(Boolean value) {
		if (value)
			getItemStack().setLore("§2Activé");
		else
			getItemStack().setLore("§4Désactivé");
		this.value = value;
		getParent().update(this);
	}
	
	@EventHandler
	public void onAlgaeCatch(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof org.bukkit.entity.Player && e.getItem().getItemStack().getType() == Material.TALL_GRASS && value) {
			e.setCancelled(true);
			e.getEntity().setHealth(e.getEntity().getHealth()-1);
			e.getItem().remove();
		}
	}
	
	public boolean isActivated() {
		return value;
	}

	@Override
	public String getDescription() {
		return null;
	}
}
