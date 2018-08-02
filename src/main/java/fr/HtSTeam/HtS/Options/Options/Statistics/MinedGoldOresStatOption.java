package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class MinedGoldOresStatOption extends OptionBuilder<Boolean> {
	
	public MinedGoldOresStatOption() {
		super(Material.GOLD_ORE, "Mined Gold", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.MINED_GOLDORES.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.MINED_GOLDORES.setTracked(false);
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
	public void on(BlockBreakEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.MINED_GOLDORES.isTracked() && e.getBlock().getType() == Material.GOLD_ORE)
			StatisticHandler.update(e.getPlayer(), EnumStats.MINED_GOLDORES);
	}
}