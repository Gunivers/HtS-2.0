package fr.HtSTeam.HtS.Options;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.ItemStackManager;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.Options.EventsOption;

public abstract class OptionsManager implements Listener, EventsOption {
	
	public static Map<OptionsManager, Object> optionsList = new HashMap<OptionsManager, Object>();
	
	private ItemStackManager icon;
	Object defaultValue;
	
	public OptionsManager(Material material, String name, String description, Object defaultValue) {
			icon = new ItemStackManager(material, (short) 0, 1, name, description, false);
			this.defaultValue = defaultValue;
			OptionsManager.optionsList.put(this, defaultValue);
			PluginManager pm = Main.plugin.getServer().getPluginManager();
			pm.registerEvents(this, Main.plugin);
	}
	
	public OptionsManager(Material material, String name, String description, boolean defaultValue) {
			icon = new ItemStackManager(material, (short) 0, 1, name, description, defaultValue);
			OptionsManager.optionsList.put(this, defaultValue);
	}
	
	public String getDescription() { return icon.getLore(); }
	public String getName() { return icon.getName(); }
	public ItemStackManager getItemStackManager() { return icon; }
	
	

}
