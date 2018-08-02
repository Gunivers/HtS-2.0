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

public class PotionThrownStatOption extends OptionBuilder<Boolean> {
	
	public PotionThrownStatOption() {
		super(Material.SPLASH_POTION, "Potion Thrown", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.POTION_THROWN.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.POTION_THROWN.setTracked(false);
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
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.POTION_THROWN.isTracked() && e.getEntityType() == EntityType.SPLASH_POTION && e.getEntity().getShooter() instanceof Player)
			StatisticHandler.update((Player) e.getEntity().getShooter(), EnumStats.POTION_THROWN);
	}
}