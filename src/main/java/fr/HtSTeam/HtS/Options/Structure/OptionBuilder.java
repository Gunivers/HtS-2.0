package fr.HtSTeam.HtS.Options.Structure;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public abstract class OptionBuilder implements Listener, EventsOption {
	
	public static Map<OptionBuilder, Object> optionsList = new HashMap<OptionBuilder, Object>();
	
	private ItemStackBuilder icon;
	private Object defaultValue;
	protected GUIBuilder parent;
	private String value;
	
	
	/**
	 * @param material
	 * 		Item représentant l'option dans le menu 
	 * @param name
	 * 		Nom de l'item
	 * @param description
	 * 		Description de l'item
	 * @param defaultValue
	 * 		Valeur par défaut. Sert de comparaison à la valeur actuel pour afficher ou nom le changement au récapitulatif du /start
	 * @param gui
	 * 		Inventaire où sera placé l'item
	 */
	public OptionBuilder(Material material, String name, String description, String defaultValue, GUIBuilder gui) {
			parent = gui;
			if(description != null)
				description = "§r" + description;
			this.icon = new ItemStackBuilder(material, (short) 0, 1, "§r" + name, description);
			this.defaultValue = defaultValue;
			this.value = defaultValue;
			OptionBuilder.optionsList.put(this, defaultValue);
			PluginManager pm = Main.plugin.getServer().getPluginManager();
			pm.registerEvents(this, Main.plugin);
			this.addAt(gui);
	}
	
	/**
	 * @param material un ItemStackManager
	 * @param defaultValue
	 * 		Valeur par défaut. Sert de comparaison à la valeur actuel pour afficher ou nom le changement au récapitulatif du /start
	 * @param gui
	 * 		Inventaire où sera placé l'item
	 */
	public OptionBuilder(ItemStackBuilder material, String defaultValue, GUIBuilder gui) {
			parent = gui;
			if(material.getLore() != null)
				material.setLore("§r" + material.getLore());
			this.icon = material;
			this.defaultValue = defaultValue;
			this.value = defaultValue;
			OptionBuilder.optionsList.put(this, defaultValue);
			PluginManager pm = Main.plugin.getServer().getPluginManager();
			pm.registerEvents(this, Main.plugin);
			this.addAt(gui);
	}
	
	public String getDescription() { return icon.getLore(); }
	public String getName() { return icon.getName(); }
	public ItemStackBuilder getItemStack() { return icon; }
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
