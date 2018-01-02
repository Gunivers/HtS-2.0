package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.Structure.GUIManager;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class GameModeState extends OptionsManager {
	

	public GameModeState(Material material, String name, String description, String defaultValue, GUIManager gui) {
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
