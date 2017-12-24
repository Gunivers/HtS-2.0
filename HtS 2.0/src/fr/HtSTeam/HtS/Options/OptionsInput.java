package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;

public class OptionsInput extends OptionsManager implements Listener {

	public OptionsInput(String name, String description, Material material, String defaultValue) {
		super(name, description, material);
		OptionsManager.optionsList.put(this, defaultValue);
		PluginManager pm = Main.plugin.getServer().getPluginManager();
		pm.registerEvents(this, Main.plugin);
	}

}
