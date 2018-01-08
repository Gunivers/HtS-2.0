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

import fr.HtSTeam.HtS.Utils.ItemStackManager;

public class GUIBuilder extends OptionBuilder {
	
	public static ArrayList<GUIBuilder> guiList = new ArrayList<GUIBuilder>();
	public Map<ItemStackManager, OptionBuilder> guiContent = new HashMap<ItemStackManager, OptionBuilder>();
	
	protected Inventory inv;
	
	
	public GUIBuilder(String name, int rows, String nameIcon, String description, Material material, GUIBuilder gui) {
		super(material, nameIcon, description, null, gui);
		guiList.add(this);
		if (rows > 6)
			return;
		inv = Bukkit.createInventory(null, rows * 9, name);		
		if(gui != null)
			addReturnButton();
	}
	
	// Common Methods
	
	public void put(OptionBuilder optionsManager) {
		if (guiContent.entrySet().size() > inv.getSize())
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
	
	public void update(OptionBuilder om) {
		for(Entry<ItemStackManager, OptionBuilder> is : guiContent.entrySet()) {
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
			GUIBuilder parent2 = parent;
			OptionBuilder om = new OptionBuilder(Material.BARRIER, "Retour", null, null, null) {

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
		for(Entry<ItemStackManager, OptionBuilder> ism : guiContent.entrySet()) {
			if(ism.getKey().getItemStack().equals(e.getCurrentItem())) {
				e.setCancelled(true);
				ism.getValue().event((Player) e.getWhoClicked());
			}
		}
	}
	
	@Override
	public void event(Player p) {
		refresh(p);
	}
}
