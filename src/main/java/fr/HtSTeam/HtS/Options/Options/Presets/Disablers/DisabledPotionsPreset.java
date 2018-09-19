package fr.HtSTeam.HtS.Options.Options.Presets.Disablers;

import org.bukkit.Material;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import fr.HtSTeam.HtS.Options.Options.Presets.Disabler;

public class DisabledPotionsPreset extends Disabler
{

	public DisabledPotionsPreset(String name, String nameIcon, String description, Material material, int page_id_max)
	{
		super(name, nameIcon, description, material, 2);
	}
	
	@Override
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e)
	{
		super.onPlayerClick(e);
		
		try
		{
			@SuppressWarnings("unused")
			PotionMeta pm = (PotionMeta) e.getCurrentItem().getItemMeta();
		} catch (ClassCastException ex)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBrewItem(BrewEvent e)
	{
		ItemStack[] potions = {e.getContents().getItem(0), e.getContents().getItem(1), e.getContents().getItem(2)};
		
		for (ItemStack item : this.items)
		{
			for (ItemStack potion : potions)
			{
				if (((PotionMeta) item.getItemMeta()).getBasePotionData().equals(((PotionMeta) potion.getItemMeta()).getBasePotionData()))
				{
					e.setCancelled(true);
					return;
				}
			}
		}
	}
}
