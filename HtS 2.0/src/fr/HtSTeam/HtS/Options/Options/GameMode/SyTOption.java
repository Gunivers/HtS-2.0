package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;
import fr.HtSTeam.HtS.Options.OptionRegister;

public class SyTOption extends GameModeState {

	public SyTOption() {
		super("Stress your Target", 1, "Stress your Target", "", Material.BOW, OptionRegister.gameMode);
	}
	
	@Override
	public void event(Player p) {
		Main.gamemode = new SyT();
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		if(getItemStack().isGlint())
			super.onClick(e);
		else
			System.out.println("aaa");
	}

}
