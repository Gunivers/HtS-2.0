package fr.HtSTeam.HtS.Players;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Utils.ItemStackManager;

public class DeathLoot {

	private List<ItemStack> deathLoot = new ArrayList<ItemStack>();
	
	
	public List<ItemStack> getDeathLoot() { return deathLoot; }
	
	
	public void addItem(ItemStackManager ism) {
		deathLoot.add(ism.getItemStack());
	}
	
	public void addItem(Material material) {
		deathLoot.add(new ItemStack(material, 1, (short) 0));
	}
	
	public void removeItem(Material material) {
		for(ItemStack im : deathLoot)
			if(im.getType().equals(material))
				deathLoot.remove(im);
	}
	
}
