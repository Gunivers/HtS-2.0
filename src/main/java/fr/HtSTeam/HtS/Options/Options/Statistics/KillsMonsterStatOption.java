package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class KillsMonsterStatOption extends OptionBuilder<Boolean> {
	
	public KillsMonsterStatOption() {
		super(Material.SKULL_ITEM, "Kills Monster", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
		parent.update(this);
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue()) {
			EnumStats.KILLS_MONSTER.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.KILLS_MONSTER.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
	}

	@Override
	public String description() {
		return null;
	}
	
	@EventHandler
	public void on(EntityDeathEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.KILLS_MONSTER.isTracked() && (e.getEntity() instanceof Monster || e.getEntityType() == EntityType.SLIME))
			StatisticHandler.update(e.getEntity().getKiller(), EnumStats.KILLS_MONSTER);
	}
}