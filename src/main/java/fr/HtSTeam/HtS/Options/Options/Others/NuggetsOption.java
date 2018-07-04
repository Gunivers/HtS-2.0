package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class NuggetsOption extends OptionBuilder<Boolean> implements Alterable {

	public NuggetsOption() {
		super(Material.GOLD_NUGGET, "Loot des pépites", "§2Activé", true, OptionRegister.other);
	}

	@Override
	public void event(Player p) {
		setState(getValue());
	}
	
	
	@Override
	public void setState(boolean value) {
		if (value)
			getItemStack().setLore("§2Activé");
		else
			getItemStack().setLore("§4Désactivé");
		setValue(value);
		parent.update(this);
	}
	

	@EventHandler
	public void onDropNuggets(PlayerBucketFillEvent e) {
		if (getValue()) {
			Player p = e.getPlayer();
			Block b = e.getBlockClicked();
			if (b.getType() == Material.STATIONARY_WATER) {
				if (Randomizer.RandRate(4))
					p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, 1));
				else if (Randomizer.RandRate(6))
					p.getInventory().addItem(new ItemStack(Material.IRON_NUGGET, 1));
			}
		}
	}
}
