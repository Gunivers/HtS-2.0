package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;

public class TimeSprintedStatOption extends OptionBuilder<Boolean> implements StartTrigger, EndTrigger {
	
	private final double player_velocity = 5.612 * 100; // cm.s^-1
	
	public TimeSprintedStatOption() {
		super(Material.WATCH, "Time Sprinted", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue() && !EnumState.getState().equals(EnumState.RUNNING)) {
			EnumStats.TIME_SPRINTED.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.TIME_SPRINTED.setTracked(false);
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
		if (EnumStats.TIME_SPRINTED.isTracked())
			Bukkit.getOnlinePlayers().forEach(player -> { player.setStatistic(Statistic.SPRINT_ONE_CM, 0); });
	}
	
	@Override
	public void onPartyEnd() {
		if (EnumStats.TIME_SPRINTED.isTracked())	
			Bukkit.getOnlinePlayers().forEach(player -> { StatisticHandler.update(player, EnumStats.TIME_SPRINTED, (int)(player.getStatistic(Statistic.SPRINT_ONE_CM) / player_velocity)); });
	}
}