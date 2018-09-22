package fr.HtSTeam.HtS.Options.Options.Presets.Disablers;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.Options.Presets.Disablers.Structure.Disabler;


public class DisabledEnchantsPreset extends Disabler
{
	private static final String name = "Enchantements Impossibles";

	/**<strong>Constructor</strong>
	 * <br \>Construct a disabler with:
	 * <ul>
	 *  <li> name = "Enchantements Impossibles"
	 *  <li> icon name = "§4Enchantements Impossibles"
	 *  <li> description = "Désactive des enchantements"
	 *  <li> icon = Material.ENCHANTED_BOOK
	 *  <li> max_page_id = 2
	 * </ul>
	 */
	public DisabledEnchantsPreset()
	{
		super(name, "§4" + name, "Désactive des enchantements", Material.ENCHANTED_BOOK, 2);
	}

	/**
	 * It cancels the event if the item isn't an enchanted_book.
	 * @see - JavaDoc of the overridden method
	 */
	@Override
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e)
	{
		super.onPlayerClick(e);
		
		if (!e.getCurrentItem().isSimilar(new ItemStack(Material.ENCHANTED_BOOK, 1)))
		{
			e.setCancelled(true);
			return;
		}
		
		if (e.getCurrentItem().getEnchantments().size() > 1)
		{
			e.getWhoClicked().sendMessage("§4Wow, tu te calmes! Un seul enchantement à la fois!");
			e.setCancelled(true);
		}
	}
	
	/**
	 * If the enchantment is disabled, this method cancels the event.
	 * 
	 * @param e
	 *         an EnchantItemEvent
	 */
	@EventHandler
	public void onEnchantItem(EnchantItemEvent e)
	{
		e.setCancelled(true);
		
		Map<Enchantment, Integer> enchants = e.getEnchantsToAdd();
		
		for (ItemStack item : this.items)
		{
			Entry<Enchantment, Integer> disabled = item.getEnchantments().entrySet().iterator().next();
			
			for (Entry<Enchantment, Integer> enchant : enchants.entrySet())
			{
				if (enchant.equals(disabled))
				{
					enchants.remove(enchant.getKey());
				}
			}
			
			if (enchants.size() == 0)
			{
				e.setCancelled(true);
				return;
			}
		}
		
		Player p = e.getEnchanter();
		
		if (p.getLevel() < e.getExpLevelCost())
		{
			return;
		}
		
		p.setLevel(p.getLevel() - e.getExpLevelCost());
		e.getItem().addEnchantments(enchants);
	}
}
