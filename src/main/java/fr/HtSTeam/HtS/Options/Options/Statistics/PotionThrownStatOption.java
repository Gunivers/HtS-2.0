package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class PotionThrownStatOption extends Option<Boolean> {
	
	public PotionThrownStatOption() {
		super(Material.SPLASH_POTION, "Potion Thrown", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.POTION_THROWN.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.POTION_THROWN.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}
	
	@EventHandler
	public void on(ProjectileLaunchEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.POTION_THROWN.isTracked() && e.getEntityType() == EntityType.SPLASH_POTION && e.getEntity().getShooter() instanceof Player)
			StatisticHandler.update(((Entity) e.getEntity().getShooter()).getUniqueId(), EnumStats.POTION_THROWN);
	}
}