package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.GameRule;
import org.bukkit.Material;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class DayLightCycleOption extends Option<Boolean> {
	
	public DayLightCycleOption() {
		super(Material.TALL_GRASS, "Cycle jour/nuit", "§2Activé", true, GUIRegister.base);
		Main.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
	}

	@Override
	public void event(Player p) {
		setState(!value);	
	}

	@Override
	public void setState(Boolean value) {
		this.value = value;
		Main.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, value);
		if(value)
			getItemStack().setLore("§2Activé");
		else 
			getItemStack().setLore("§4Désactivé");
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r Le cycle jour/nuit est désactivé.";
	}
}
