package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class NuggetsOption extends OptionsManager {

	private boolean activate = false;

	public NuggetsOption() {
		super(Material.GOLD_NUGGET, "Loot des pépites", "§2Activé", "Activé", OptionsRegister.other);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		if (activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@EventHandler
	public void onDropNuggets(PlayerBucketFillEvent e) {
		if (activate) {
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
