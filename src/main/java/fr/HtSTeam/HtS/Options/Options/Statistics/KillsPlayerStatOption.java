package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Bukkit;
import org.bukkit.Material;import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;

public class KillsPlayerStatOption extends OptionBuilder<Boolean> implements StartTrigger, EndTrigger {
	
	public KillsPlayerStatOption() {
		super(Material.SKULL_ITEM, "Kills Player", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.KILLS_PLAYER.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.KILLS_PLAYER.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		StatisticHandler.updateTrackedStats();
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}
	
	@Override
	public void onPartyStart() {
		if (EnumStats.KILLS_PLAYER.isTracked())
			Bukkit.getOnlinePlayers().forEach(player -> { player.setStatistic(Statistic.PLAYER_KILLS, 0); });
	}

	@Override
	public void onPartyEnd() {
		if (EnumStats.KILLS_PLAYER.isTracked())
			Bukkit.getOnlinePlayers().forEach(player -> { StatisticHandler.update(player, EnumStats.KILLS_PLAYER, player.getStatistic(Statistic.PLAYER_KILLS)); });
	}
}