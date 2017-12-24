package fr.HtSTeam.HtS.Options;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import fr.HtSTeam.HtS.ItemStackManager;

public abstract class OptionsManager {
	
	public static Map<OptionsManager, Object> optionsList = new HashMap<OptionsManager, Object>();
	
	private ItemStackManager icon;
	
	public OptionsManager(String name, String description, Material material) {
		icon = new ItemStackManager(material, (short) 0, 1, name, description, false);
	}
	
	public String getDescription() { return icon.getLore(); }
	public String getName() { return icon.getName(); }
	public ItemStackManager getItemStackManager() { return icon; }
	
	

}
