package fr.HtSTeam.HtS.Options.Options.Presets.Disablers;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.Options.Presets.Disabler;

public class DisabledCraftsPreset extends Disabler
{
	private static final String name = "Crafts Impossibles";
	
	public DisabledCraftsPreset()
	{
		super(name, "§4" + name, "Désactive le craft d'items", Material.STICK, 9);
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent e)
	{
		for (ItemStack item : this.items)
		{
			if (e.getCurrentItem().isSimilar(item))
			{
				e.setCancelled(true);
				return;
			}
		}
	}
}
