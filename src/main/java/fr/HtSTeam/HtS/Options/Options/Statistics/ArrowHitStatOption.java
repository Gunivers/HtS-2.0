package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class ArrowHitStatOption extends OptionBuilder<Boolean> {
	
	public ArrowHitStatOption() {
		super(Material.SPECTRAL_ARROW, "Arrow Hit", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue() && !EnumState.getState().equals(EnumState.RUNNING)) {
			EnumStats.ARROW_HIT.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.ARROW_HIT.setTracked(false);
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
	public void on(ProjectileHitEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.ARROW_HIT.isTracked() && e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player && e.getHitEntity() != null)
			StatisticHandler.update((Player) e.getEntity().getShooter(), EnumStats.ARROW_HIT);
	}
}