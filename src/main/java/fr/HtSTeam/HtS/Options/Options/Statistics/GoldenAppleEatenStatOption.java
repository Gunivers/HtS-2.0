package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class GoldenAppleEatenStatOption extends OptionBuilder<Boolean> {
	
	public GoldenAppleEatenStatOption() {
		super(Material.GOLDEN_APPLE, "Golden Apple Eaten", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
		parent.update(this);
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue()) {
			EnumStats.GOLDEN_APLLE_EATEN.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.GOLDEN_APLLE_EATEN.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
	}

	@Override
	public String description() {
		return null;
	}
	
	@EventHandler
	public void on(PlayerItemConsumeEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.GOLDEN_APLLE_EATEN.isTracked() && e.getItem().getType() == Material.GOLDEN_APPLE)
			StatisticHandler.update(e.getPlayer(), EnumStats.GOLDEN_APLLE_EATEN);
	}
}