package fr.HtSTeam.HtS.Options.Options.Presets;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class DisabledCraftsPreset extends GUIBuilder implements OptionIO
{
	private static final String name = "Crafts Impossibles";
	private static final int page_id_max = 9;
	
	ItemStack[] items = {};
	private int page_id;

	private final ItemStackBuilder nothing = new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE, 1, "", "");
	
	private final ItemStackBuilder save = new ItemStackBuilder(Material.ENDER_CHEST, 1, "§4Sauvegarder", "");
	private final ItemStackBuilder page = new ItemStackBuilder(Material.PAPER, 1, "§5Page 1", "");
	
	private final ItemStackBuilder prevPageArrow = new ItemStackBuilder(Material.ARROW, 1, "§7Page précédente", "");
	private final ItemStackBuilder nextPageArrow = new ItemStackBuilder(Material.ARROW, 1, "§7Page suivante", "");

	
	public DisabledCraftsPreset()
	{
		super("§4" + name, 9, name, "Empêche le craft d'items", Material.STRUCTURE_VOID, GUIRegister.presets);
	}

	@Override
	public void event(Player p)
	{
		p.closeInventory();
		this.page_id = 1;
		
		this.refresh(p);
	}

	@Override
	public void refresh(Player p)
	{
		this.inv.clear();
		
		for (int i = 72 * (this.page_id -1); i < 72 * this.page_id; i++)
		{
			if (i >= this.items.length) break;
			
			this.inv.addItem(items[i]);
		}
		
		this.page.setName("§5Page" + this.page_id);

		this.addReturnButton();
		
		this.inv.setItem(72, this.save);
		
		this.inv.setItem(73, this.nothing);
		this.inv.setItem(74, this.nothing);
		
		this.inv.setItem(75, this.prevPageArrow);
		this.inv.setItem(76, this.page);
		this.inv.setItem(77, this.nextPageArrow);

		this.inv.setItem(78, this.nothing);
		this.inv.setItem(79, this.nothing);
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e)
	{
		if (!e.getInventory().getName().equals(inv.getName())) return;
		
		if (e.getCurrentItem() == this.nothing || e.getCurrentItem() == this.page)
		{
			e.setCancelled(true);
			return;
		}
		
		if (e.getCurrentItem() == this.save)
		{
			e.setCancelled(true);
			
			for (int i = 0; i < 72; i++)
			{
				this.items[i + 72 * this.page_id] = new ItemStack(Material.AIR);
				
				ItemStack item = this.inv.getItem(i);
				if (item != null) this.items[i + 72 * this.page_id] = item;
			}
			
			this.save();
		}
		
		if (e.getCurrentItem() == this.prevPageArrow)
		{
			e.setCancelled(true);
			if (this.page_id == 1) return;
			
			this.page_id--;
			this.refresh((Player) e.getWhoClicked());
		}
		
		if (e.getCurrentItem() == nextPageArrow)
		{
			e.setCancelled(true);
			if (this.page_id == page_id_max) return;
			
			this.page_id++;
			this.refresh((Player) e.getWhoClicked());
		}
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent e)
	{
		if (Arrays.asList(items).contains(e.getCurrentItem()))
		{
			e.setCancelled(true);
		}
	}
	
	@Override
	public void load(Object o)
	{
		try
		{
			ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode((String) o));
	        BukkitObjectInputStream data2 = new BukkitObjectInputStream(stream);
	        
			ItemStack is;
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			
			try
			{
				while(true)
				{
					is = (ItemStack) data2.readObject();
					list.add(is);
				}
			}
			catch(EOFException e) {}
			
			finally
			{
				data2.close();
			}
			
			this.items = list.toArray(new ItemStack[72 * page_id_max]);
			
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			
			for (Player p : Bukkit.getOnlinePlayers())
			{
				if (p.isOp())
					p.sendMessage("§5[§2" + Main.HTSNAME + "§5]"
							+ "\n - §7An error occured while loading"
							+ "\n§4" + e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<String> save()
	{
		if(this.items.length == 0)
			return null;
		try
		{
			ByteArrayOutputStream str = new ByteArrayOutputStream();	
			BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
			
			for(ItemStack i : this.items)
					data.writeObject(i);
			
			data.close();
			
			ArrayList<String> elements = new ArrayList<String>();
			elements.add(Base64.getEncoder().encodeToString(str.toByteArray()));
			
			return elements;
		} catch (IOException e)
		{
			e.printStackTrace();
			
			for (Player p : Bukkit.getOnlinePlayers())
			{

				if (p.isOp())
					p.sendMessage("§6[§2" + Main.HTSNAME + "§6]"
							+ "\n - §5An error occured while saving: §6DisabledCraftPreset"
							+ "\n§4" + e.getMessage());
			}
			
			return null;
		}
	}

	@Override
	public String getId() 
	{
		return this.getName();
	}
}
