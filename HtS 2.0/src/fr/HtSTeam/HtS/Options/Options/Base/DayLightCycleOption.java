package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DayLightCycleOption extends OptionBuilder {
	
	private boolean activate = true;

	public DayLightCycleOption() {
		super(Material.DOUBLE_PLANT, "Cycle jour/nuit", "§2Activé", "Activé", OptionRegister.base);
		Main.world.setGameRuleValue("doDaylightCycle", "true");
		
	}

	@Override
	public void event(Player p) {
		activate =! activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
			Main.world.setGameRuleValue("doDaylightCycle", "true");
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
			Main.world.setGameRuleValue("doDaylightCycle", "false");
		}		
		parent.update(this);
	}
}
