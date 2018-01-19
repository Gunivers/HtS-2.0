package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.Options.OptionRegister;

public class UHCOption extends GameModeState {

	
	public UHCOption() {
		super("UHC", 1, "UHC", "§2Sélectionné", Material.GOLDEN_APPLE, OptionRegister.gameMode);
		this.getItemStack().setGlint(true);
		parent.update(this);
	}
	
	@Override
	public void event(Player p) {
		Main.gamemode = new UHC();
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		if(getItemStack().isGlint())
			super.onClick(e);
		else
			System.out.println("aaa");
	}
}
