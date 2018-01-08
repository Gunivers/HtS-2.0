package fr.HtSTeam.HtS.Options.Structure;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Utils.ItemStackManager;

public abstract class OptionBuilder implements Listener, EventsOption {
	
	public static Map<OptionBuilder, Object> optionsList = new HashMap<OptionBuilder, Object>();
	
	private ItemStackManager icon;
	private Object defaultValue;
	protected GUIBuilder parent;
	private String value;
	
	public OptionBuilder(Material material, String name, String description, String defaultValue, GUIBuilder gui) {
			parent = gui;
			if(description != null)
				description = "§r" + description;
			this.icon = new ItemStackManager(material, (short) 0, 1, "§r" + name, description, false);
			this.defaultValue = defaultValue;
			this.value = defaultValue;
			OptionBuilder.optionsList.put(this, defaultValue);
			PluginManager pm = Main.plugin.getServer().getPluginManager();
			pm.registerEvents(this, Main.plugin);
			this.addAt(gui);
	}
	
	public String getDescription() { return icon.getLore(); }
	public String getName() { return icon.getName(); }
	public ItemStackManager getItemStackManager() { return icon; }
	public Object getDefaultValue() { return defaultValue; }
	
	public void setValue(String value) {
		this.value = value;
		OptionBuilder.optionsList.replace(this, value);
	}
	public String getValue() { return value; }

	public void addAt(GUIBuilder gm) { 		
		if(gm != null) 
			gm.put(this);
		}
	
	public GUIBuilder getParent() { return parent; }
	

}
