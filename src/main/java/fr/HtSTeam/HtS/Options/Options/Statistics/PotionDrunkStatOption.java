package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class PotionDrunkStatOption extends OptionBuilder<Boolean> {
	
	public PotionDrunkStatOption() {
		super(Material.POTION, "Potion Drunk", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.POTION_DRUNK.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.POTION_DRUNK.setTracked(false);
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
	public void on(PlayerItemConsumeEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.POTION_DRUNK.isTracked() && e.getItem().getType() == Material.POTION)
			StatisticHandler.update(e.getPlayer(), EnumStats.POTION_DRUNK);
	}
}