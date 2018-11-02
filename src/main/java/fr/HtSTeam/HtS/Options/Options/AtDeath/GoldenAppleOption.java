package fr.HtSTeam.HtS.Options.Options.AtDeath;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class GoldenAppleOption extends Option<Boolean> {
	
	public GoldenAppleOption() {
		super(Material.GOLDEN_APPLE, "Drop de pomme d'or", "§2Activé", true, GUIRegister.atDeath);
//		Main.deathLoot.addItem(Material.GOLDEN_APPLE);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if(value && !getValue()) {
				getItemStack().setLore("§2Activé");
//				Main.deathLoot.addItem(Material.GOLDEN_APPLE);
		} else if(!value && getValue()){
			getItemStack().setLore("§4Désactivé");
//			Main.deathLoot.removeItem(Material.GOLDEN_APPLE);
		}
		setValue(value);
		parent.update(this);		
	}

	@Override
	public String description() {
		return "§2[Aide]§r À la mort d'un joueur, ce dernier droppera une pomme d'or.";
	}
}
