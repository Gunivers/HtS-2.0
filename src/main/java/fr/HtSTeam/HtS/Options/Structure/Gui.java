package fr.HtSTeam.HtS.Options.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class Gui extends Icon {
	
	public static ArrayList<Gui> guiList = new ArrayList<>();
	public Map<ItemStackBuilder, Icon> guiContent = new HashMap<>();
	
	protected Inventory inv;
	private int maxSize;
	private int nextPlacement;
	
	
	public Gui(String name, int rows, String nameIcon, String description, Material material, Gui gui) {
		this(name, rows, new ItemStackBuilder(material, 1, "§r" + nameIcon, description), gui, -1);
	}
	
	public Gui(String name, String nameIcon, String description, Material material, Gui gui) {
		this(name, 1, nameIcon, description, material, gui, -1);
	}
	
	public Gui(String name, ItemStackBuilder it, Gui gui) {
		this(name, 1, it, gui, -1);
	}
	
	public Gui(String name, int rows, String nameIcon, String description, Material material, Gui gui, int slot) {
		this(name, rows, new ItemStackBuilder(material, 1, "§r" + nameIcon, description), gui, slot);
	}
	
	public Gui(String name, String nameIcon, String description, Material material, Gui gui, int slot) {
		this(name, 1, nameIcon, description, material, gui, slot);
	}
	
	public Gui(String name, ItemStackBuilder it, Gui gui, int slot) {
		this(name, 1, it, gui, slot);
	}
	
	public Gui(String name, int rows, ItemStackBuilder it, Gui gui, int slot) {
		super(it, gui);
		guiList.add(this);
		rows = rows > 6 ? 6 : rows; 
		nextPlacement = 0;
		maxSize = rows * 9;
		inv = Bukkit.createInventory(null, maxSize, name);		
		if(gui != null)
			addReturnButton();
	}
	
	// Common Methods
	
	public void put(Icon option) {
		if (nextPlacement >= maxSize) {
			ItemStack[] content = inv.getContents();
			if(getParent() != null)
				content[content.length - 1] = null;
			Inventory inv2 = Bukkit.createInventory(null, (maxSize += 9) + (maxSize % 9 == 0 ? 0 : 1), inv.getName());
			inv2.setContents(inv.getContents());
			inv = inv2;
		}
		guiContent.put(option.getItemStack(), option);
		inv.setItem(nextPlacement, option.getItemStack());	
	}
	
	public void put(Icon option, int slot) {
		if(slot >= maxSize || slot < 0)
			throw new ArrayIndexOutOfBoundsException("Le slot spécifié n'est pas compris dans l'inventaire !");
		inv.setItem(slot, option.getItemStack());
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
	
	public void update(Icon om) {
		for (Entry<ItemStackBuilder, Icon> is : guiContent.entrySet()) {
			if (is.getValue() == om)
				for (ItemStack is2 : inv.getContents())
					if (is2 != null && is2.getItemMeta().getDisplayName().equals(is.getKey().getName())) {
						inv.setItem(Arrays.asList(inv.getContents()).indexOf(is2), om.getItemStack());
						return;

					}
		}
	}
	
	protected void addReturnButton() {
		if(getParent() != null) {
			int rand = Randomizer.rand(1000000000);
			String res = Integer.toString(rand);
    		String news = "";
    		for(int i = 0; i < res.length(); i++)
    			news += "§" + res.charAt(i);
    		ItemStackBuilder itemStack = new ItemStackBuilder(Material.BARRIER, 1, "§rRetour", news);
			new Icon(itemStack, getParent(), maxSize) {

				@Override
				public void event(Player p) {
					getParent().refresh(p);
				}		
			};
		}
	
	}
	
	
	// Events
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		for(Entry<ItemStackBuilder, Icon> ism : guiContent.entrySet()) {
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
