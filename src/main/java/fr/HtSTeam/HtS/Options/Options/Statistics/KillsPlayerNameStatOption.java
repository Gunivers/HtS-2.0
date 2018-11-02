package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class KillsPlayerNameStatOption extends Option<Boolean> {
	
	public KillsPlayerNameStatOption() {
		super(Material.PLAYER_HEAD, "KILLS_PLAYER_NAME", "§2Activé", true, GUIRegister.stats);	
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
			EnumStats.KILLS_PLAYER_NAME.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.KILLS_PLAYER_NAME.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}
	
	@EventHandler
	public void on(PlayerDeathEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.KILLS_PLAYER_NAME.isTracked() && e.getEntity().getKiller() instanceof Player)
			StatisticHandler.update(e.getEntity().getKiller().getUniqueId(), EnumStats.KILLS_PLAYER_NAME, e.getEntity().getName());
	}
}
