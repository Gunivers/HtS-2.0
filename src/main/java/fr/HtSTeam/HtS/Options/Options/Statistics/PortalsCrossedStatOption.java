package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Player.Player;

public class PortalsCrossedStatOption extends OptionBuilder<Boolean> {
	
	public PortalsCrossedStatOption() {
		super(Material.NETHER_PORTAL, "Portals Crossed", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.PORTALS_CROSSED.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.PORTALS_CROSSED.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}
	
	@EventHandler
	public void on(PlayerPortalEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.PORTALS_CROSSED.isTracked())
			StatisticHandler.update(e.getPlayer().getUniqueId(), EnumStats.PORTALS_CROSSED);
	}
}