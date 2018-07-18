package fr.HtSTeam.HtS.Options.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class GUIBuilder extends IconBuilder<Null> {
	
	public static ArrayList<GUIBuilder> guiList = new ArrayList<GUIBuilder>();
	public Map<ItemStackBuilder, IconBuilder<?>> guiContent = new HashMap<ItemStackBuilder, IconBuilder<?>>();
	
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
	
	public GUIBuilder(String name, int rows, ItemStackBuilder its, GUIBuilder gui) {
		super(its, null, gui);
		guiList.add(this);
		if (rows > 6)
			return;
		inv = Bukkit.createInventory(null, rows * 9, name);		
		if(gui != null)
			addReturnButton();
	}
	
	// Common Methods
	
	public void put(IconBuilder<?> optionsManager) {
		if (guiContent.entrySet().size() > inv.getSize())
			return;
		guiContent.put(optionsManager.getItemStack(), optionsManager);
		if(parent == null)
			inv.setItem(guiContent.entrySet().size() - 1, optionsManager.getItemStack());
		else
			inv.setItem(guiContent.entrySet().size() - 2, optionsManager.getItemStack());		
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
	
	public void update(IconBuilder<?> om) {
		for(Entry<ItemStackBuilder, IconBuilder<?>> is : guiContent.entrySet()) {
			if(is.getValue() == om) {
				for(ItemStack is2 : inv.getContents()) {
					if(is2 != null && is2.getItemMeta().getDisplayName().equals(is.getKey().getName())) {
						inv.setItem(Arrays.asList(inv.getContents()).indexOf(is2), om.getItemStack());
						return;
					}
				}
				
			}
		}
	}
	
	public void addReturnButton() {
		if(!parent.equals(null)) {
			GUIBuilder parent2 = parent;
			int rand = Randomizer.Rand(1000000000);
			String res = Integer.toString(rand);
    		String news = "";
    		for(int i = 0; i < res.length(); i++)
    			news += "§" + res.charAt(i);
    		ItemStackBuilder itemStack = new ItemStackBuilder(Material.BARRIER, (short) 0, 1, "§rRetour", news);
			IconBuilder<Null> om = new IconBuilder<Null>(itemStack, null, null) {

				@Override
				public void event(Player p) {
					parent2.refresh(p);
				}		
			};
		
			inv.setItem(inv.getSize() - 1, om.getItemStack());	
			guiContent.put(om.getItemStack(), om);
			
		}
	
	}
	
	
	// Events
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		for(Entry<ItemStackBuilder, IconBuilder<?>> ism : guiContent.entrySet()) {
			if(ism.getKey().equals(e.getCurrentItem())) {
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
