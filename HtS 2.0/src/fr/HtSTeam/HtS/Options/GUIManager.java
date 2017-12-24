package fr.HtSTeam.HtS.Options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIManager extends OptionsManager {
	
	public static ArrayList<GUIManager> guiList = new ArrayList<GUIManager>();
	public Map<ItemStack, OptionsManager> guiContent = new HashMap<ItemStack, OptionsManager>();
	
	private Inventory inv;
	
	public GUIManager(String name, int rows, String nameIcon, String description, Material material) {
		super(material, description, nameIcon, null);
		guiList.add(this);
		if (rows > 6)
			return;
		inv = Bukkit.createInventory(null, rows * 9, name);		
	}
	
	public void put(OptionsManager optionsManager) {
		if (guiContent.entrySet().size() >= inv.getSize())
			return;
		guiContent.put(optionsManager.getItemStackManager().getItemStack(), optionsManager);
		inv.setItem(guiContent.entrySet().size() - 1, optionsManager.getItemStackManager().getItemStack());		
	}
	
	public void open(Player p) {
		p.openInventory(inv);
	}
	
	public void close(Player p) {
		p.closeInventory();
	}
	
	public void update(Player p) {
		p.closeInventory();
		p.openInventory(inv);
	}
}
