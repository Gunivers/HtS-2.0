package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.FallenKingdom;
import fr.HtSTeam.HtS.Options.OptionsRegister;

public class FallenKingdomOption extends GameModeState {

	public FallenKingdomOption() {
		super(Material.CHAINMAIL_CHESTPLATE, "Fallen Kingdom", "", "", OptionsRegister.gameMode);
	}
	
	@Override
	public void event(Player p) {
		Main.gamemode = new FallenKingdom();
	}

}