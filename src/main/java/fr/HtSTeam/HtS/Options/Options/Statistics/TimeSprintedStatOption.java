package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class TimeSprintedStatOption extends OptionBuilder<Boolean> {
	
	public TimeSprintedStatOption() {
		super(Material.WATCH, "Time Sprinted", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.TIME_SPRINTED.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.TIME_SPRINTED.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
	}

	@Override
	public String description() {
		return null;
	}
}