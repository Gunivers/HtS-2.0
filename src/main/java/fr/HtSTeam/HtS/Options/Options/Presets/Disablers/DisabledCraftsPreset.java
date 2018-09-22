package fr.HtSTeam.HtS.Options.Options.Presets.Disablers;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.Options.Presets.Disablers.Structure.Disabler;

/**
 * This is the Disabler of Crafts
 * 
 * @author A~Z
 *
 */
public class DisabledCraftsPreset extends Disabler
{
	private static final String name = "Crafts Impossibles";
	
	/**<strong>Constructor</strong>
	 * <br \>Construct a disabler with:
	 * <ul>
	 *  <li> name = "Crafts Impossibles"
	 *  <li> icon name = "§4Crafts Impossibles"
	 *  <li> description = "Désactive le craft d'items"
	 *  <li> icon = Material.STICK
	 *  <li> max_page_id = 9
	 * </ul>
	 */
	public DisabledCraftsPreset()
	{
		super(name, "§4" + name, "Désactive le craft d'items", Material.STICK, 9);
	}
	
	/**
	 * If the craft is disabled, this method cancels the event.
	 * 
	 * @param e
	 *         a CraftItemEvent
	 */
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
