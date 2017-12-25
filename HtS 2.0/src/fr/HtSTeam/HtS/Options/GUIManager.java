package fr.HtSTeam.HtS.Options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIManager extends OptionsManager {
	
	public static ArrayList<GUIManager> guiList = new ArrayList<GUIManager>();
	public Map<ItemStack, OptionsManager> guiContent = new HashMap<ItemStack, OptionsManager>();
	
	private Inventory inv;
	private GUIManager parent;
	
	public GUIManager(String name, int rows, String nameIcon, String description, Material material, GUIManager gui) {
		super(material, description, nameIcon, null, gui);
		guiList.add(this);
		parent = gui;
		if (rows > 6)
			return;
		inv = Bukkit.createInventory(null, rows * 9, name);		
		if(gui != null)
			addReturnButton();
	}
	
	// Common Methods
	
	public void put(OptionsManager optionsManager) {
		if (guiContent.entrySet().size() >= inv.getSize() - 1)
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
	
	public void addReturnButton() {
		if(parent != null) {
			OptionsManager om = new OptionsManager(Material.BARRIER, "Retour", null, null, null) {

				@Override
				public void event(Player p) {
					parent.update(p);
					
				}
				
			};
		
			inv.setItem(inv.getSize() - 1, om.getItemStackManager().getItemStack());	
			guiContent.put(om.getItemStackManager().getItemStack(), om);
			
		}
	
	}
	
	
	// Events
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (guiContent.containsKey(e.getCurrentItem())) {
			e.setCancelled(true);
			guiContent.get(e.getCurrentItem()).event((Player) e.getWhoClicked());
		}
	}
	
	@Override
	public void event(Player p) {
		update(p);
	}
}
