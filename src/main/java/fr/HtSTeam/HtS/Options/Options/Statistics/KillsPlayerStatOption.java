package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class KillsPlayerStatOption extends OptionBuilder<Boolean> {
	
	public KillsPlayerStatOption() {
		super(Material.SKULL_ITEM, "Kills Player", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.KILLS_PLAYER.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.KILLS_PLAYER.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
	}

	@Override
	public String description() {
		return null;
	}
}