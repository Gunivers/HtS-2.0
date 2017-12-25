package fr.HtSTeam.HtS.Options.Structure;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.ItemStackManager;
import fr.HtSTeam.HtS.Main;

public abstract class OptionsManager implements Listener, EventsOption {
	
	public static Map<OptionsManager, Object> optionsList = new HashMap<OptionsManager, Object>();
	
	private ItemStackManager icon;
	private Object defaultValue;
	protected GUIManager parent;
	private String value;
	
	public OptionsManager(Material material, String name, String description, String defaultValue, GUIManager gui) {
			parent = gui;
			this.icon = new ItemStackManager(material, (short) 0, 1, name, description, false);
			this.defaultValue = defaultValue;
			this.value = defaultValue;
			OptionsManager.optionsList.put(this, defaultValue);
			PluginManager pm = Main.plugin.getServer().getPluginManager();
			pm.registerEvents(this, Main.plugin);
			this.addAt(gui);
	}
	
	public OptionsManager(Material material, String name, String description, boolean defaultValue, GUIManager gui) {
			parent = gui;
			this.icon = new ItemStackManager(material, (short) 0, 1, name, description, defaultValue);
			this.defaultValue = defaultValue;
			OptionsManager.optionsList.put(this, defaultValue);
			this.addAt(gui);
	}
	

	public String getDescription() { return icon.getLore(); }
	public String getName() { return icon.getName(); }
	public ItemStackManager getItemStackManager() { return icon; }
	public Object getDefaultValue() { return defaultValue; }
	
	public void setValue(String value) {
		this.value = value;
		OptionsManager.optionsList.replace(this, value);
	}
	public String getValue() { return value; }

	public void addAt(GUIManager gm) { 		
		if(gm != null) 
			gm.put(this);
		}
	

}
