package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DamageGivenStatOption extends OptionBuilder<Boolean> {
	
	public DamageGivenStatOption() {
		super(Material.GOLD_SWORD, "Damage Given", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue() && !EnumState.getState().equals(EnumState.RUNNING)) {
			EnumStats.DAMAGE_GIVEN.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.DAMAGE_GIVEN.setTracked(false);
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
	public void on(EntityDamageByEntityEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.DAMAGE_GIVEN.isTracked() && e.getDamager() instanceof Player)
			StatisticHandler.update((Player) e.getDamager(), EnumStats.DAMAGE_GIVEN, (int)e.getDamage());
	}
}