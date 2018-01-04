package fr.HtSTeam.HtS.Options.Options.AtDeath;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class HeadOption extends OptionsManager implements Alterable {
	
	private boolean activate = true;

	public HeadOption() {
		super(Material.SKULL_ITEM, "Drop de tête", "§2Activé", "Activé", OptionsRegister.atDeath);
		getItemStackManager().setItem(Material.SKULL_ITEM, (short) 3);
		parent.update(this);
		Main.deathLoot.addItem(Material.SKULL_ITEM, (short) 3);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		setState(activate);
	}

	@Override
	public void setState(boolean value) {
		activate = value;
		if(value) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
			Main.deathLoot.addItem(Material.SKULL_ITEM, (short) 3);
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
			Main.deathLoot.removeItem(Material.SKULL_ITEM);
		}
		parent.update(this);
	}

}
