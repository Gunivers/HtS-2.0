package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class AccuracyStatOption extends OptionBuilder<Boolean> implements EndTrigger {
	
	public AccuracyStatOption() {
		super(Material.BOW, "Accuracy", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.ACCURACY.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.ACCURACY.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}

	@Override
	public void onPartyEnd() {
		if (EnumStats.ACCURACY.isTracked() && EnumStats.ARROW_HIT.isTracked() && EnumStats.ARROW_SHOT.isTracked())
			Bukkit.getOnlinePlayers().forEach(player -> { StatisticHandler.update(player, EnumStats.ACCURACY, (int)((int)StatisticHandler.get(player, EnumStats.ARROW_HIT) * 100 / (int)StatisticHandler.get(player, EnumStats.ARROW_SHOT))); });
	}
}