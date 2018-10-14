package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.FallenKingdom;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Player.Player;

public class SyTOption extends GameModeState {

	public SyTOption() {
		super("Stress your Target", 1, "Stress your Target", null, Material.BOW, GUIRegister.gameMode);
	}
	
	@Override
	public void event(Player p) {	
		if (!SyT.instance) {
			Main.gamemode = new SyT();
			UHC.instance = false;
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
