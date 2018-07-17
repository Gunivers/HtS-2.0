package fr.HtSTeam.HtS.Options.Options.AtDeath;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class HeadOption extends OptionBuilder<Boolean> {
	
	public HeadOption() {
		super(Material.SKULL_ITEM, "Drop de tête", "§2Activé", true, GUIRegister.atDeath);
		getItemStack().setItem(Material.SKULL_ITEM, (short) 3);
		parent.update(this);
		Main.deathLoot.addItem(Material.SKULL_ITEM, (short) 3);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if(value && !getValue()) {
			if(getValue().equals(true)) {
				getItemStack().setLore("§2Activé");
				Main.deathLoot.addItem(Material.SKULL_ITEM, (short) 3);
			}
		} else if(!value && getValue()) {
			getItemStack().setLore("§4Désactivé");
			Main.deathLoot.removeItem(Material.SKULL_ITEM);
		}
		setValue(value);
		parent.update(this);
	}
}
