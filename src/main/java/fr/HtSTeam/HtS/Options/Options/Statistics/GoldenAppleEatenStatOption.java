package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class GoldenAppleEatenStatOption extends Option<Boolean> {
	
	public GoldenAppleEatenStatOption() {
		super(Material.GOLDEN_APPLE, "Golden Apple Eaten", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.GOLDEN_APPLE_EATEN.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.GOLDEN_APPLE_EATEN.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}
	
	@EventHandler
	public void on(PlayerItemConsumeEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.GOLDEN_APPLE_EATEN.isTracked() && e.getItem().getType() == Material.GOLDEN_APPLE)
			StatisticHandler.update(e.getPlayer().getUniqueId(), EnumStats.GOLDEN_APPLE_EATEN);
	}
}