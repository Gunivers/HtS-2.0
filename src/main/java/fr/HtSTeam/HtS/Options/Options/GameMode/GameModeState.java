package fr.HtSTeam.HtS.Options.Options.GameMode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;

public abstract class GameModeState extends GUIBuilder {
	

	public GameModeState(String nameInv, int row, String name, String description, Material material, GUIBuilder gui) {
		super(nameInv, row, name, description, material, gui);
		GameModeGUI.gameModeOption.add(this);
	}
	
	public void setState(boolean b) {
		if(b) {
			this.getItemStack().setLore("§2Sélectionné");
			this.getItemStack().setGlint(true);
		} else {
			this.getItemStack().setLore(null);
			this.getItemStack().setGlint(false);
		}
		parent.update(this);
	}

	@Override
	public void event(Player p) {}
	
	public abstract void setOption();
	
	@EventHandler
	@Override
	public void onClick(InventoryClickEvent e) {
		super.onClick(e);
	}
}