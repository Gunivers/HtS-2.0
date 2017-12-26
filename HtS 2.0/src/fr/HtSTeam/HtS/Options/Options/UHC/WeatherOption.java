package fr.HtSTeam.HtS.Options.Options.UHC;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class WeatherOption extends OptionsManager {
	
	private boolean activate = true;

	public WeatherOption() {
		super(Material.WATER_BUCKET, "Pluie", "§2Activé", "Activé", OptionsRegister.uhc);
	}

	@Override
	public void event(Player p) {
		activate =! activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
			getItemStackManager().setItem(Material.WATER_BUCKET, (short) 0);
			
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
			getItemStackManager().setItem(Material.BUCKET, (short) 0);
		}		
		parent.update(this);
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		if(!activate && e.toWeatherState()) 
			e.setCancelled(true);
	}

}
