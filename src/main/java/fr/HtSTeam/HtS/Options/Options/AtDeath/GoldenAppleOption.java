package fr.HtSTeam.HtS.Options.Options.AtDeath;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class GoldenAppleOption extends OptionBuilder implements Alterable {
	
	private boolean activate = true;

	public GoldenAppleOption() {
		super(Material.GOLDEN_APPLE, "Drop de pomme d'or", "§2Activé", "Activé", OptionRegister.atDeath);
		Main.deathLoot.addItem(Material.GOLDEN_APPLE, (short) 0);
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
			if(!getValue().equals("Activé")) {
				setValue("Activé");
				getItemStack().setLore("§2Activé");
				System.out.println("§aGoldenApple instantiated!");
				Main.deathLoot.addItem(Material.GOLDEN_APPLE, (short) 0);
			}
		} else {
			setValue("Désactivé");
			getItemStack().setLore("§4Désactivé");
			Main.deathLoot.removeItem(Material.GOLDEN_APPLE);
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
