package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class ArrowHitStatOption extends Option<Boolean> {
	
	public ArrowHitStatOption() {
		super(Material.SPECTRAL_ARROW, "Arrow Hit", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if (EnumState.getState().equals(EnumState.RUNNING))
			return;
		this.value = value;
		if(getValue()) {
			EnumStats.ARROW_HIT.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.ARROW_HIT.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}
	
	@EventHandler
	public void on(ProjectileHitEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.ARROW_HIT.isTracked() && e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player && e.getHitEntity() != null)
			StatisticHandler.update(((Entity) e.getEntity().getShooter()).getUniqueId(), EnumStats.ARROW_HIT);
	}
}