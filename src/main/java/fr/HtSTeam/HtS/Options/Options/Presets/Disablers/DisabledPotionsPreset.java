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
	private static final String name = "Potions Impossibles";
	/**<strong>Constructor</strong>
	 * <br \>Construct a disabler with:
	 * <ul>
	 *  <li> name = "Potions Impossibles"
	 *  <li> icon name = "§4Potions Impossibles"
	 *  <li> description = "Désactive la création de potions"
	 *  <li> icon = Material.POTION
	 *  <li> max_page_id = 1
	 * </ul>
	 */
	public DisabledPotionsPreset()
	{
		super(name, "§4" + name, "Désactive la création de potions", Material.POTION, 1);
	}
	
	/**
	 * It cancels the event if the item isn't a potion.
	 * @see - JavaDoc of the overridden method
	 */
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
	
	/**
	 * If the potion is disabled, this method cancels the event.
	 * 
	 * @param e
	 *         a BrewEvent
	 */
	@EventHandler
	public void onBrewPotion(BrewEvent e)
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
