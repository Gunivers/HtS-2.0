package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.FallenKingdom;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.OptionRegister;

public class FallenKingdomOption extends GameModeState {

	public FallenKingdomOption() {
		super("Fallen Kingdom", 1, "Fallen Kingdom", null, Material.CHAINMAIL_CHESTPLATE, GUIRegister.gameMode);
	}
	
	@Override
	public void event(Player p) {
		if (!FallenKingdom.instance) {
			Main.gamemode = new FallenKingdom();
			UHC.instance = false;
			SyT.instance = false;
		}
		if(p != null && getItemStack().isGlint())
			open(p);
	}

	@Override
	public void setOption() {
		OptionRegister.noRegen.setState(true);
		OptionRegister.goldenApple.setState(false);
	}
}
