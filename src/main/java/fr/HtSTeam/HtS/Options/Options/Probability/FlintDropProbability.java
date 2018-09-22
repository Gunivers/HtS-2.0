package fr.HtSTeam.HtS.Options.Options.Probability;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Events.Structure.EventHandler;
import fr.HtSTeam.HtS.Options.Options.Probability.Structure.Probability;

/**
 * 
 * @author A~Z
 *
 */
public class FlintDropProbability extends Probability
{	
	public FlintDropProbability()
	{
		super(Material.FLINT, "Drop de Silex", "§630.0§2%", 30.D);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if (Material.GRAVEL == e.getBlock().getType() && random.nextDouble() < this.getValue())
		{
			e.setCancelled(true);
			this.dropFlint(e.getBlock());
		}
	}
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent e)
	{
		if (Material.GRAVEL == e.getBlock().getType() && random.nextDouble() < this.getValue())
		{
			e.setCancelled(true);
			this.dropFlint(e.getBlock());
		}
	}
	
	public void dropFlint(Block block)
	{
		block.setType(Material.AIR);
		block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT, 1));
	}
}
