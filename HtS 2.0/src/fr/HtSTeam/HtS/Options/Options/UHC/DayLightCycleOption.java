package fr.HtSTeam.HtS.Options.Options.UHC;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class DayLightCycleOption extends OptionsManager {
	
	private boolean activate = false;

	public DayLightCycleOption() {
		super(Material.DOUBLE_PLANT, "Cycle jour/nuit", "§2Activé", "Activé", OptionsRegister.uhc);
		
	}

	@Override
	public void event(Player p) {
		activate =! activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
		}		
	}
	
	@EventHandler
	public void onTimeChange(WeatherChangeEvent e) {
		if(!activate)
			e.setCancelled(true);
	}

}
