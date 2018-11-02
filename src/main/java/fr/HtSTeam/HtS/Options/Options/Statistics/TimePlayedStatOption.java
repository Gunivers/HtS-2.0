package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.DeathTrigger;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class TimePlayedStatOption extends Option<Boolean> implements EndTrigger, DeathTrigger {
	
	public TimePlayedStatOption() {
		super(Material.CLOCK, "Time Played", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.TIME_PLAYED.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.TIME_PLAYED.setTracked(false);
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
//		if (EnumStats.TIME_PLAYED.isTracked())
//			PlayerInGame.playerInGame.forEach(uuid -> { StatisticHandler.update(uuid, EnumStats.TIME_PLAYED, Main.timer.getTimerInSeconds()); });
	}

	@Override
	public void onDeath(Player p) {
		if (EnumStats.TIME_PLAYED.isTracked())
			StatisticHandler.update(p.getUUID(), EnumStats.TIME_PLAYED, Main.timer.getTimerInSeconds());
	}
}