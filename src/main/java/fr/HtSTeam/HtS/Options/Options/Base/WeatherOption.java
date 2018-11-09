package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class WeatherOption extends Option<Boolean> {
	

	public WeatherOption() {
		super(Material.WATER_BUCKET, "Pluie", "§2Activé", true, GUIRegister.base);
	}

	@Override
	public void event(Player p) {
		setState(!value);
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		if(!value && e.toWeatherState()) 
			e.setCancelled(true);
	}

	@Override
	public void setState(Boolean value) {
		if(value) {
			getItemStack().setLore("§2Activé");
			getItemStack().setItem(Material.WATER_BUCKET);
			
		} else {
			getItemStack().setLore("§4Désactivé");
			getItemStack().setItem(Material.BUCKET);
		}	
		this.value = value;
		getParent().update(this);		
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r La pluie est désactivée.";
	}

}
