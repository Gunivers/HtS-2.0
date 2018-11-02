package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.WorldBorder;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.ChatNumberOption;

public class BorderOption extends ChatNumberOption {
	
	private WorldBorder border = Main.world.getWorldBorder();
	
	public BorderOption() {
		super(Material.IRON_BARS, "Taille de la bordure", "§d1000 * 1000", 1000, GUIRegister.base, 500, 2500);
		border.setCenter(0.0, 0.0);
		border.setSize(1000);
	}
	
	@Override
	public void setState(Integer value) {
		setValue(value * 2);	
		this.getItemStack().setLore("§d" + value * 2 + " * " + value * 2);
		border.setSize(value * 2);
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r La bordure se trouve à " + getValue() + " blocs du centre de la map.";
	}

	@Override
	public void dispValidMessage() {
		p.sendMessage("§2Bordure à " + getValue() + " blocs du centre." );		
	}

	@Override
	public void dispRequestMessage() {
		p.sendMessage("Veuillez saisir la taille de la bordure.");
	}
}
