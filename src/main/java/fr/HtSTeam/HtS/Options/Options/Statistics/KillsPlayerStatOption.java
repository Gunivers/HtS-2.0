package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Player.Player;

public class KillsPlayerStatOption extends OptionBuilder<Boolean> {
	
	public KillsPlayerStatOption() {
		super(Material.PLAYER_HEAD, "Kills Player", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.KILLS_PLAYER.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.KILLS_PLAYER.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}
	
	@EventHandler
	public void on(PlayerDeathEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.KILLS_PLAYER.isTracked() && e.getEntity().getKiller() instanceof Player)
			StatisticHandler.update(e.getEntity().getKiller().getUniqueId(), EnumStats.KILLS_PLAYER);
	}
}