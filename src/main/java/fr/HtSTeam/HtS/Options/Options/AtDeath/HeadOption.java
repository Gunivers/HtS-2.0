package fr.HtSTeam.HtS.Options.Options.AtDeath;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class HeadOption extends OptionBuilder implements Alterable {
	
	private boolean activate = true;

	public HeadOption() {
		super(Material.SKULL_ITEM, "Drop de tête", "§2Activé", "Activé", OptionRegister.atDeath);
		getItemStack().setItem(Material.SKULL_ITEM, (short) 3);
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
			getItemStack().setLore("§2Activé");
			Main.deathLoot.addItem(Material.SKULL_ITEM, (short) 3);
		} else {
			setValue("Désactivé");
			getItemStack().setLore("§4Désactivé");
			Main.deathLoot.removeItem(Material.SKULL_ITEM);
		}
		parent.update(this);
	}
	
	public boolean isActivated() {
		if (getValue().equals("Activé"))
			return true;
		else
			return false;
	}
}
