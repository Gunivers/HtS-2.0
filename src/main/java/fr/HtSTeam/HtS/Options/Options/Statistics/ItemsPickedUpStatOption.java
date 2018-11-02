package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class ItemsPickedUpStatOption extends Option<Boolean> {
	
	public ItemsPickedUpStatOption() {
		super(Material.STICK, "Items Picked Up", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if (EnumState.getState().equals(EnumState.RUNNING))
			return;
		setValue(value);
		if(getValue()) {
			EnumStats.ITEMS_PICKED_UP.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.ITEMS_PICKED_UP.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}
	
	@EventHandler
	public void on(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player && EnumState.getState().equals(EnumState.RUNNING) && EnumStats.ITEMS_PICKED_UP.isTracked())
			StatisticHandler.update(e.getEntity().getUniqueId(), EnumStats.ITEMS_PICKED_UP);
	}
}