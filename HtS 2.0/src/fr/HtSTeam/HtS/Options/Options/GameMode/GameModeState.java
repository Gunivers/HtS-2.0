package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class GameModeState extends OptionBuilder {
	

	public GameModeState(Material material, String name, String description, String defaultValue, GUIBuilder gui) {
		super(material, name, description, defaultValue, gui);
		((GameModeGUI) parent).gameModeOption.add(this);
	}
	
	public void setState(boolean b) {
		if(b) {
			this.getItemStackManager().setLore("§2Sélectionné");
			this.getItemStackManager().setGlint(true);
		} else {
			this.getItemStackManager().setLore(null);
			this.getItemStackManager().setGlint(false);
		}
		parent.update(this);
	}

	@Override
	public void event(Player p) {}
	
}
