package fr.HtSTeam.HtS.Options.Options.Modifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIManager;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Utils.ItemStackManager;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public class ModifiersGUI extends GUIManager implements StartTrigger {
	
	private Map<Player, CustomGUI> customInventory = new HashMap<Player, CustomGUI>();

	public ModifiersGUI() {
		super("Modifiers", 1, "Modifiers", "Activer/Désactiver des items modifiés", Material.END_CRYSTAL, OptionsRegister.main);
	}
	
	@Override
	public void event(Player p) {
		super.event(p);
	}
	
	@Override
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		for(Entry<ItemStackManager, OptionsManager> ism : guiContent.entrySet()) {
			if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.BARRIER) && ism.getKey().getItemStack().equals(e.getCurrentItem())) {
				e.setCancelled(true);
				ism.getValue().event((Player) e.getWhoClicked());
			}
		}
	}

	@Override
	public void onPartyStart() {
		boolean active = false;
		for(Entry<ItemStackManager, OptionsManager> entry : guiContent.entrySet())
			if(entry.getValue().getValue() != null && entry.getValue().getValue().equals("Activé")) {
				active = true;
				continue;
			}
		if(!active) return;
		for(Player p : Main.playerInGame.getPlayerInGame())
			customInventory.put(p, new CustomGUI());
	}
}
