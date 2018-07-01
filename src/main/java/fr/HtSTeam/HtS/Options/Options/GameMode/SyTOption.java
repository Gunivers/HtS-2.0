package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.FallenKingdom;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;
import fr.HtSTeam.HtS.Options.OptionRegister;

public class SyTOption extends GameModeState {

	public SyTOption() {
		super("Stress your Target", 1, "Stress your Target", "", Material.BOW, OptionRegister.gameMode);
	}
	
	@Override
	public void event(Player p) {	
		if (!SyT.instance) {
			Main.gamemode = new SyT();
			UHC.instance = false;
			FallenKingdom.instance = false;
		}
		if(getItemStack().isGlint())
			open(p);
	}

	@Override
	public void setOption() {
		OptionRegister.noRegen.setState(false);
		OptionRegister.goldenApple.setState(true);		
	}
}
