package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Player.Player;

public class TimeSneakedStatOption extends Option<Boolean> implements StartTrigger, EndTrigger {
	
	public TimeSneakedStatOption() {
		super(Material.CLOCK, "Time Sneaked", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.TIME_SNEAKED.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.TIME_SNEAKED.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}
	
	@Override
	public void onPartyStart() {
		if (EnumStats.TIME_SNEAKED.isTracked())
			Bukkit.getOnlinePlayers().forEach(player -> { player.setStatistic(Statistic.SNEAK_TIME, 0); });
	}
	
	@Override
	public void onPartyEnd() {
		if (EnumStats.TIME_SNEAKED.isTracked())
			Bukkit.getOnlinePlayers().forEach(player -> { StatisticHandler.update(player.getUniqueId(), EnumStats.TIME_SNEAKED, (int)(player.getStatistic(Statistic.SNEAK_TIME) / 22)); });
	}
}