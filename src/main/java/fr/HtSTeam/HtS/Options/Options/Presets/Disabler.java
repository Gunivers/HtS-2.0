package fr.HtSTeam.HtS.Options.Options.Presets;

import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;

public abstract class Disabler extends GUIBuilder implements OptionIO
{
	protected static final int rows = 8;
	protected final int page_id_max;
	
	protected ItemStack[] items = new ItemStack[0];
	protected int page_id = 1;

	protected final ItemStackBuilder nothing = new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE, 1, "", "");
	
	protected final ItemStackBuilder save = new ItemStackBuilder(Material.SHULKER_BOX, 1, "§4Sauvegarder", "");
	protected final ItemStackBuilder page = new ItemStackBuilder(Material.PAPER, 1, "§5Page 1", "");
	
	protected final ItemStackBuilder prevPageArrow = new ItemStackBuilder(Material.ARROW, 1, "§7Page précédente", "");
	protected final ItemStackBuilder nextPageArrow = new ItemStackBuilder(Material.ARROW, 1, "§7Page suivante", "");
	
	public Disabler(String name, String nameIcon, String description, Material material, int page_id_max)
	{
		super(name, rows +1, nameIcon, description, material, GUIRegister.presets);
		this.page_id_max = page_id_max;
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
		
		this.page.setName("§5Page" + this.page_id);

		this.addReturnButton();
		
		this.inv.setItem(9*rows, this.save);
		
		this.inv.setItem(9*rows +1, this.nothing);
		this.inv.setItem(9*rows +2, this.nothing);
		
		this.inv.setItem(9*rows +3, this.prevPageArrow);
		this.inv.setItem(9*rows +4, this.page);
		this.inv.setItem(9*rows +5, this.nextPageArrow);

		this.inv.setItem(9*rows +6, this.nothing);
		this.inv.setItem(9*rows +7, this.nothing);

		for (int i = 9*rows * (this.page_id -1); i < 9*rows * this.page_id; i++)
		{
			if (i >= this.items.length) break;
			
			this.inv.addItem(items[i]);
		}
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
			
			for (int i = 0; i < 9*rows; i++)
			{
				this.items[i + 9*rows * this.page_id] = new ItemStack(Material.AIR);
				
				ItemStack item = this.inv.getItem(i);
				if (item != null) this.items[i + 9*rows * this.page_id] = item;
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
	
	@Override
	public void load(Object o)
	{
		try (ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode((String) o)))
		{   
			ItemStack is;
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			
			try (BukkitObjectInputStream data2 = new BukkitObjectInputStream(stream))
			{
				while(true)
				{
					is = (ItemStack) data2.readObject();
					list.add(is);
				}
			}
			catch(EOFException e) {}
			
			this.items = list.toArray(new ItemStack[9*rows * page_id_max]);
			
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			
			for (Player p : Bukkit.getOnlinePlayers())
			{
				if (p.isOp())
					p.sendMessage("§5[§2" + Main.HTSNAME + "§5]"
							+ "\n - §7An error occured while loading: §6" + this.getClass().getSimpleName()
							+ "\n§4" + e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<String> save()
	{
		if(this.items.length == 0)
			return null;
		
		try (ByteArrayOutputStream str = new ByteArrayOutputStream(); BukkitObjectOutputStream data = new BukkitObjectOutputStream(str))
		{
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
							+ "\n - §5An error occured while saving: §6" + this.getClass().getSimpleName()
							+ "\n§4" + e.getMessage());
			}
			
			return null;
		}
	}

	public ItemStack[] getItems()
	{
		return items;
	}
	
	@Override
	public String getId()
	{
		return this.getName();
	}
}
