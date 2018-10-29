package fr.HtSTeam.HtS.Options.Options.AtDeath;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Player.Player;

public class HeadOption extends OptionBuilder<Boolean> {
	
	public HeadOption() {
		super(Material.PLAYER_HEAD, "Drop de tête", "§2Activé", true, GUIRegister.atDeath);
		parent.update(this);
//		Main.deathLoot.addItem(Material.PLAYER_HEAD);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if(value && !getValue()) {
			getItemStack().setLore("§2Activé");
//			Main.deathLoot.addItem(Material.PLAYER_HEAD);
		} else if(!value && getValue()) {
			getItemStack().setLore("§4Désactivé");
//			Main.deathLoot.removeItem(Material.PLAYER_HEAD);
		}
		setValue(value);
		parent.update(this);
	}

	@Override
	public String description() {
		return "§2[Aide]§r À la mort du joueur, ce dernier droppera sa tête (simple objet décoratif).";
	}
}
