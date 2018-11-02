package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class KillsPassiveStatOption extends Option<Boolean> {
	
	public KillsPassiveStatOption() {
		super(Material.PLAYER_HEAD, "Kills Passive", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.KILLS_PASSIVE.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.KILLS_PASSIVE.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}
	
	@EventHandler
	public void on(EntityDeathEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.KILLS_PASSIVE.isTracked() && e.getEntity() instanceof Animals && e.getEntity().getKiller() instanceof Player)
			StatisticHandler.update(e.getEntity().getKiller().getUniqueId(), EnumStats.KILLS_PASSIVE);
	}
}