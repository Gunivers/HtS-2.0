package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DayLightCycleOption extends OptionBuilder<Boolean> {
	
	public DayLightCycleOption() {
		super(Material.DOUBLE_PLANT, "Cycle jour/nuit", "§2Activé", true, OptionRegister.base);
		Main.world.setGameRuleValue("doDaylightCycle", "true");
		
	}

	@Override
	public void event(Player p) {
		setValue(!getValue());
		if(getValue())
			getItemStack().setLore("§2Activé");
		 else 
			getItemStack().setLore("§4Désactivé");	
		Main.world.setGameRuleValue("doDaylightCycle", Boolean.toString(getValue()));
		parent.update(this);
	}
}
