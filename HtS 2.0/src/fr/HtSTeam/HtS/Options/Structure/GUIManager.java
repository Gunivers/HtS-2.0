package fr.HtSTeam.HtS.Options.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.ItemStackManager;

public class GUIManager extends OptionsManager {
	
	public static ArrayList<GUIManager> guiList = new ArrayList<GUIManager>();
	public Map<ItemStackManager, OptionsManager> guiContent = new HashMap<ItemStackManager, OptionsManager>();
	
	private Inventory inv;
	
	
	public GUIManager(String name, int rows, String nameIcon, String description, Material material, GUIManager gui) {
		super(material, nameIcon, description, null, gui);
		guiList.add(this);
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
		guiContent.put(optionsManager.getItemStackManager(), optionsManager);
		if(parent == null)
			inv.setItem(guiContent.entrySet().size() - 1, optionsManager.getItemStackManager().getItemStack());
		else
			inv.setItem(guiContent.entrySet().size() - 2, optionsManager.getItemStackManager().getItemStack());		
	}
	
	public void open(Player p) {
		p.openInventory(inv);
	}
	
	public void close(Player p) {
		p.closeInventory();
	}
	
	public void refresh(Player p) {
		p.closeInventory();
		p.openInventory(inv);
	}
	
	public void update(OptionsManager om) {
		for(Entry<ItemStackManager, OptionsManager> is : guiContent.entrySet()) {
			if(is.getValue() == om) {
				for(ItemStack is2 : inv.getContents()) {
					if(is2 != null && is2.getItemMeta().getDisplayName().equals(is.getKey().getName())) {
						inv.setItem(Arrays.asList(inv.getContents()).indexOf(is2), om.getItemStackManager().getItemStack());
						return;
					}
				}
				
			}
		}
	}
	
	public void addReturnButton() {
		if(!parent.equals(null)) {
			GUIManager parent2 = parent;
			OptionsManager om = new OptionsManager(Material.BARRIER, "Retour", null, null, null) {

				@Override
				public void event(Player p) {
					parent2.refresh(p);
					
				}
				
			};
		
			inv.setItem(inv.getSize() - 1, om.getItemStackManager().getItemStack());	
			guiContent.put(om.getItemStackManager(), om);
			
		}
	
	}
	
	
	// Events
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		for(Entry<ItemStackManager, OptionsManager> ism : guiContent.entrySet()) {
			if(ism.getKey().getItemStack().equals(e.getCurrentItem())) {
				e.setCancelled(true);
				ism.getValue().event((Player) e.getWhoClicked());;
			}
		}
	}
	
	@Override
	public void event(Player p) {
		refresh(p);
	}
}
