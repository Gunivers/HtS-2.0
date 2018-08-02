package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class ArrowShotStatOption extends OptionBuilder<Boolean> {
	
	public ArrowShotStatOption() {
		super(Material.ARROW, "Arrow Shot", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue() && !EnumState.getState().equals(EnumState.RUNNING)) {
			EnumStats.ARROW_SHOT.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.ARROW_SHOT.setTracked(false);
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
	public void on(ProjectileLaunchEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.ARROW_SHOT.isTracked() && e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player)
			StatisticHandler.update((Player) e.getEntity().getShooter(), EnumStats.ARROW_SHOT);
	}
}