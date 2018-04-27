package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.FallenKingdom;
import fr.HtSTeam.HtS.Options.OptionRegister;

public class FallenKingdomOption extends GameModeState {

	public FallenKingdomOption() {
		super("Fallen Kingdom", 1, "Fallen Kingdom", "", Material.CHAINMAIL_CHESTPLATE, OptionRegister.gameMode);
	}
	
	@Override
	public void event(Player p) {
		Main.gamemode = new FallenKingdom();
		if(getItemStack().isGlint())
			open(p);
	}
}
