package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DayLightCycleOption extends OptionBuilder<Boolean> {
	
	public DayLightCycleOption() {
		super(Material.DOUBLE_PLANT, "Cycle jour/nuit", "§2Activé", true, GUIRegister.base);
		Main.world.setGameRuleValue("doDaylightCycle", "true");
		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());	
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		Main.world.setGameRuleValue("doDaylightCycle", Boolean.toString(getValue()));
		if(getValue())
			getItemStack().setLore("§2Activé");
		else 
			getItemStack().setLore("§4Désactivé");
		parent.update(this);
	}

	@Override
	public String description() {
		return "§2[Aide]§r Le cycle jour/nuit est désactivé.";
	}
}
