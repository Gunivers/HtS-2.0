package fr.HtSTeam.HtS.Options.Options.AtDeath;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class GoldenAppleOption extends OptionBuilder<Boolean> implements Alterable {
	
	public GoldenAppleOption() {
		super(Material.GOLDEN_APPLE, "Drop de pomme d'or", "§2Activé", true, OptionRegister.atDeath);
		Main.deathLoot.addItem(Material.GOLDEN_APPLE, (short) 0);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(boolean value) {
		if(value && !getValue()) {
				getItemStack().setLore("§2Activé");
				Main.deathLoot.addItem(Material.GOLDEN_APPLE, (short) 0);
		} else if(!value && getValue()){
			getItemStack().setLore("§4Désactivé");
			Main.deathLoot.removeItem(Material.GOLDEN_APPLE);
		}
		setValue(value);
		parent.update(this);		
	}
}
