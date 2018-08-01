package fr.HtSTeam.HtS.Options.Options.GameMode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;

public class GameModeGUI extends GUIBuilder {

	public static List<GameModeState> gameModeOption = new ArrayList<GameModeState>();

	public GameModeGUI() {
		super("Mode de jeux", 1, "Mode de jeux", "Choisir le mode de jeu", Material.COMMAND, GUIRegister.main);
		new GameModeIO();
	}

	@Override
	public void event(Player p) {
		super.event(p);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClicks(InventoryClickEvent e) {
		if (e.getInventory().equals(inv) && e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.BARRIER)) {
			boolean b = false;
			for (ItemStack is : e.getInventory().getContents())
				if (is != null && is.equals(e.getCurrentItem()))
					b = true;
			if (!b)
				return;
			for (GameModeState gms : gameModeOption)
				if (gms.getItemStack().equals(e.getCurrentItem())) {
					setGameMode(gms);
				}
			e.setCancelled(true);
		}
	}

	public void setGameMode(GameModeState gms) {
		gms.setState(true);
		gms.setOption();
		for (GameModeState gms2 : gameModeOption) {
			if (gms != gms2) {
				gms2.setState(false);
			}
		}
	}
	
}
