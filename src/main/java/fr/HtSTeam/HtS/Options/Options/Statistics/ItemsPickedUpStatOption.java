package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

@SuppressWarnings("deprecation")
public class ItemsPickedUpStatOption extends OptionBuilder<Boolean> {
	
	public ItemsPickedUpStatOption() {
		super(Material.STICK, "Items Picked Up", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
		parent.update(this);
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue() && !EnumState.getState().equals(EnumState.RUNNING)) {
			EnumStats.ITEMS_PICKED_UP.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.ITEMS_PICKED_UP.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		StatisticHandler.updateTrackedStats();
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}
	
	@EventHandler
	public void on(PlayerPickupItemEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.ITEMS_PICKED_UP.isTracked())
			StatisticHandler.update(e.getPlayer(), EnumStats.ITEMS_PICKED_UP);
	}
}