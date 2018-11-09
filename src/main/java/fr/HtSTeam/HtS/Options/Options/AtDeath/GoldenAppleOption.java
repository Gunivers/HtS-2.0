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
		setState(!value);
	}

	@Override
	public void setState(Boolean value) {
		if(value && !value) {
				getItemStack().setLore("§2Activé");
//				Main.deathLoot.addItem(Material.GOLDEN_APPLE);
		} else if(!value && value){
			getItemStack().setLore("§4Désactivé");
//			Main.deathLoot.removeItem(Material.GOLDEN_APPLE);
		}
		this.value = value;
		getParent().update(this);		
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r À la mort d'un joueur, ce dernier droppera une pomme d'or.";
	}
}
