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
	private Object defaultValue;
	
	public OptionsManager(Material material, String name, String description, Object defaultValue, GUIManager gui) {
			this.icon = new ItemStackManager(material, (short) 0, 1, name, description, false);
			this.defaultValue = defaultValue;
			OptionsManager.optionsList.put(this, defaultValue);
			PluginManager pm = Main.plugin.getServer().getPluginManager();
			pm.registerEvents(this, Main.plugin);
			this.addAt(gui);
	}
	
	public OptionsManager(Material material, String name, String description, boolean defaultValue, GUIManager gui) {
			this.icon = new ItemStackManager(material, (short) 0, 1, name, description, defaultValue);
			this.defaultValue = defaultValue;
			OptionsManager.optionsList.put(this, defaultValue);
			this.addAt(gui);
	}
	

	public String getDescription() { return icon.getLore(); }
	public String getName() { return icon.getName(); }
	public ItemStackManager getItemStackManager() { return icon; }
	public Object getDefaultValue() { return defaultValue; }
	
	public void addAt(GUIManager gm) { 		
		if(gm != null) {
			System.out.println(this.getItemStackManager());
			gm.put(this);
		}
		}
	

}
