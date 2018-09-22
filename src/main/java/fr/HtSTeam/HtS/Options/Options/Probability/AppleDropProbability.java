package fr.HtSTeam.HtS.Options.Options.Probability;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.Options.Probability.Structure.Probability;
import fr.HtSTeam.HtS.Utils.Randomizer;

/**
 * 
 * @author TheDarven
 *
 */
public class AppleDropProbability extends Probability
{
	public AppleDropProbability()
	{
		super(Material.APPLE, "Drop de Pomme", "§61.0§2%", 1.0);
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e)
	{
		if((e.getBlock().getType().equals(Material.OAK_LEAVES) || e.getBlock().getType().equals(Material.DARK_OAK_LEAVES)) && !e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.SHEARS))
		{
			e.setCancelled(true);
			dropApple(e.getBlock());	
		}
	}
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent e)
	{
		if(e.getBlock().getType().equals(Material.OAK_LEAVES) || e.getBlock().getType().equals(Material.DARK_OAK_LEAVES))
		{
			e.setCancelled(true);
			dropApple(e.getBlock());
		}
	}
	
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e)
	{
		if(e.getBlock().getType().equals(Material.OAK_LEAVES) || e.getBlock().getType().equals(Material.DARK_OAK_LEAVES))
		{
			e.setCancelled(true);
			dropApple(e.getBlock());
		}
	}
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent e)
	{
		if(e.getBlock().getType().equals(Material.OAK_LEAVES) || e.getBlock().getType().equals(Material.DARK_OAK_LEAVES))
		{
			e.setCancelled(true);
			dropApple(e.getBlock());
		}
	}
	
	public void dropApple(Block block)
	{
		Location loc = block.getLocation();
		
		loc.getWorld().getBlockAt(loc).setType(Material.AIR);
		loc.setX(loc.getX() + 0.5);
		loc.setY(loc.getY() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		
		int randomValue = Randomizer.rand(200);

		if(randomValue <= getValue())
		{
			ItemStack item = new ItemStack(Material.APPLE, 1);
			loc.getWorld().dropItemNaturally(loc, item);
		}
		else if(randomValue <= getValue() + 10.0)
		{
			if(block.getType().equals(Material.OAK_LEAVES))
			{
				ItemStack item = new ItemStack(Material.OAK_SAPLING, 1);
				loc.getWorld().dropItemNaturally(loc, item);
			}
			else
			{
				ItemStack item = new ItemStack(Material.DARK_OAK_SAPLING, 1);
				loc.getWorld().dropItemNaturally(loc, item);
			}
		}		
	}
}
