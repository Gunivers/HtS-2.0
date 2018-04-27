package fr.HtSTeam.HtS.Options.Options.Modifier;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public class CustomGUI implements Listener {
	
	PluginManager pm = Bukkit.getServer().getPluginManager();
	private Inventory inv;
	public static List<ItemStackBuilder> authorizedItem = new ArrayList<ItemStackBuilder>();
	
	{
		inv = Bukkit.createInventory(null, 9, "Modifiers");
		pm.registerEvents(this, Main.plugin);
	}
	
	public void open(Player p) {
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onDrag(InventoryClickEvent e) {
		if(e.getInventory().equals(inv)) {
			if(e.getCurrentItem() != null) {
				for(ItemStackBuilder is : authorizedItem)
					if(e.getCurrentItem().getItemMeta() != null && is.getName().equals(e.getCurrentItem().getItemMeta().getDisplayName()) && is.getType().equals(e.getCurrentItem().getType()))
						return;
				if(e.getCurrentItem().getType().equals(Material.AIR) && !e.getCursor().getType().equals(Material.AIR)) 
					return;
			}
			e.setCancelled(true);
		}
			
	}
	
	public boolean contains(ItemStackBuilder is) {
		for(ItemStack is2 : inv.getContents()) {
			if(is2 != null && is2.getItemMeta() != null && is2.getItemMeta().getDisplayName().equals(is.getName()) && is2.getType().equals(is.getType()))
				return true;
		}
		return false;
	}
	
	public void removeItem(ItemStackBuilder is) {
		for(ItemStack is2 : inv.getContents())
			if(is2 != null && is2.getItemMeta() != null && is2.getItemMeta().getDisplayName().equals(is.getName()) && is2.getType().equals(is.getType()))
				inv.remove(is2);
	}
}
