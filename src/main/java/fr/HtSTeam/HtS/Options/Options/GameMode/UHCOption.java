package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.FallenKingdom;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.OptionRegister;

public class UHCOption extends GameModeState {

	
	public UHCOption() {
		super("UHC", 1, "UHC", "§2Sélectionné", Material.GOLDEN_APPLE, GUIRegister.gameMode);
		this.getItemStack().setGlint(true);
		parent.update(this);
	}
	
	@Override
	public void event(Player p) {
		if (!UHC.instance) {
			Main.gamemode = new UHC();
			SyT.instance = false;
			FallenKingdom.instance = false;
		}
		if(p != null && getItemStack().isGlint())
			open(p);
	}

	@Override
	public void setOption() {
		OptionRegister.noRegen.setState(false);
		OptionRegister.goldenApple.setState(true);	
	}
}
