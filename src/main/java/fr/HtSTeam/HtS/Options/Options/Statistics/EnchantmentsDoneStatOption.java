package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class EnchantmentsDoneStatOption extends OptionBuilder<Boolean> {
	
	public EnchantmentsDoneStatOption() {
		super(Material.ENCHANTMENT_TABLE, "Enchantments Done", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.ENCHANTMENTS_DONE.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.ENCHANTMENTS_DONE.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		StatisticHandler.updateTrackedStats();
	}

	@Override	
	public String description() {
		return null;
	}
	
	@EventHandler
	public void on(EnchantItemEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.ENCHANTMENTS_DONE.isTracked())
			StatisticHandler.update(e.getEnchanter(), EnumStats.ENCHANTMENTS_DONE);
	}
}