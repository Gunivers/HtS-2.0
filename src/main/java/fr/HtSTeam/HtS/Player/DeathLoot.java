package fr.HtSTeam.HtS.Player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public class DeathLoot {

	private List<ItemStack> deathLoot = new ArrayList<ItemStack>();
	
	
	public List<ItemStack> getDeathLoot() { return deathLoot; }
	
	
	public void addItem(ItemStackBuilder ism) {
		deathLoot.add(ism);
	}
	
	public void addItem(Material material) {
		deathLoot.add(new ItemStack(material, 1));
	}
	
	public void removeItem(Material material) {
			for(int i = 0; i < deathLoot.size(); i++)
				if(deathLoot.get(i).getType().equals(material))
					deathLoot.remove(deathLoot.get(i));
	}
	
}
