package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.UHC.UHC;
import fr.HtSTeam.HtS.Options.OptionsRegister;

public class UHCOption extends GameModeState {

	
	public UHCOption() {
		super(Material.GOLDEN_APPLE, "UHC", "§2Sélectionné", "", OptionsRegister.gameMode);
		this.getItemStackManager().setGlint(true);
		parent.update(this);
	}
	
	@Override
	public void event(Player p) {
		Main.gamemode = new UHC();
	}
	
	
}
