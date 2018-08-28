package fr.HtSTeam.HtS.Options.Options.Others;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Events.Structure.EventHandler;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class FlintOption extends OptionBuilder<Integer>
{
	private static final int max = 100;
	private static final int min = 1;
	
	private Player p;
	private boolean request = false;

	public FlintOption()
	{
		super(Material.FLINT, "Probabilité du drop de Silex", "§230%", 30, GUIRegister.other);
	}

	@Override
	public void event(Player p)
	{
		this.request = true;
		this.p = p;
		this.p.closeInventory();
		this.p.sendMessage("§2Veuillez saisir le pourcentage de drop du silex.");
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		if (request && this.p.equals(e.getPlayer()))
		{
			e.setCancelled(true);
			
			try
			{
				int value = Integer.parseInt(e.getMessage());
				this.setState(value);
				
				if (value >= min && value <= max)
				{
					p.sendMessage("§2La chance de drop du silex est de §5" + value + "§2%.");
					
					parent.update(this);
					request = false;
					
					return;
				}

				p.sendMessage("Le drop du silex a été désactivé");
				
			} catch (NumberFormatException ex)
			{
				p.sendMessage("§4La valeur saisie n'est pas un entier.");
				request = false;
			}
		}
	}
	
	@Override
	public void setState(Integer value)
	{
		this.setValue(value >= min && value <= max ? value : -1);
		this.getItemStack().setLore(value != -1 ? "§4Désactivé" : value + "%");
	}

	@Override
	public String description()
	{
		return "§5[Aide]§2 Le drop de silex " + (this.getValue() != -1 ? "a une probabilité de §5" +this.getValue() + "§2%" : "est §4désactivé");
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if (Material.GRAVEL == e.getBlock().getType() && Randomizer.randRate(this.getValue()))
		{
			this.setDrop(e.getBlock(), new ItemStackBuilder(Material.FLINT, 1, "flint", ""));
		}
	}
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent e)
	{
		if (Material.GRAVEL == e.getBlock().getType() && Randomizer.randRate(this.getValue()))
		{
			this.setDrop(e.getBlock(), new ItemStackBuilder(Material.FLINT, 1, "flint", ""));
		}
	}
	
	public void setDrop(Block block, ItemStack drop)
	{
		Collection<ItemStack> drops = block.getDrops();
		drops.clear();
		drops.add(drop);
	}
}
