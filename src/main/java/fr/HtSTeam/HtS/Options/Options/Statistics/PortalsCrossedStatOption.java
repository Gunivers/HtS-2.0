package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class PortalsCrossedStatOption extends OptionBuilder<Boolean> {
	
	public PortalsCrossedStatOption() {
		super(Material.ENDER_PORTAL_FRAME, "Portals Crossed", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue() && !EnumState.getState().equals(EnumState.RUNNING)) {
			EnumStats.PORTALS_CROSSED.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.PORTALS_CROSSED.setTracked(false);
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
	public void on(PlayerPortalEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.PORTALS_CROSSED.isTracked())
			StatisticHandler.update(e.getPlayer(), EnumStats.PORTALS_CROSSED);
	}
}