package fr.HtSTeam.HtS.Options.Options.GameMode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;

public class GameModeGUI extends GUIBuilder {
	
	public List<GameModeState> gameModeOption = new ArrayList<GameModeState>();

	public GameModeGUI() {
		super("Mode de jeux", 1, "Mode de jeux", "Choisir le mode de jeu", Material.COMMAND, OptionRegister.main);
	}
	
	@Override
	public void event(Player p) {
		super.event(p);
	}

	@EventHandler
	public void onClicks(InventoryClickEvent e) {
		for (GameModeState gms : gameModeOption) {
			if (e.getInventory().equals(inv) && e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.BARRIER)) {
				if (gms.getItemStackManager().getItemStack().equals(e.getCurrentItem())) {
					gms.setState(true);
					e.setCancelled(true);
				} else {
					gms.setState(false);
					e.setCancelled(true);
				}
			}
		}
	}

}
