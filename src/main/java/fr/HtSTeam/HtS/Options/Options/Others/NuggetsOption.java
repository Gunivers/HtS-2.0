package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class NuggetsOption extends Option<Boolean>{

	public NuggetsOption() {
		super(Material.GOLD_NUGGET, "Loot des pépites", "§2Activé", true, GUIRegister.other);
	}

	@Override
	public void event(Player p) {
		setState(value);
	}
	
	
	@Override
	public void setState(Boolean value) {
		if (value)
			getItemStack().setLore("§2Activé");
		else
			getItemStack().setLore("§4Désactivé");
		this.value = value;
		getParent().update(this);
	}
	

	@EventHandler
	public void onDropNuggets(PlayerBucketFillEvent e) {
		if (value) {
			Player p = Player.instance(e.getPlayer());
			Block b = e.getBlockClicked();
			if (b.getType() == Material.WATER) {
				if (Randomizer.randRate(4))
					p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, 1));
				else if (Randomizer.randRate(6))
					p.getInventory().addItem(new ItemStack(Material.IRON_NUGGET, 1));
			}
		}
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r Lors du remplissage d'un sceau d'eau, il y a 2% de chances que celui-ci loot une nugget d'or ou de fer.";
	}
}
