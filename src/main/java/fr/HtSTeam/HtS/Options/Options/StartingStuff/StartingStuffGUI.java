package fr.HtSTeam.HtS.Options.Options.StartingStuff;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public class StartingStuffGUI extends GUIBuilder implements StartTrigger {
	
	ItemStack[] items = new ItemStack[0];

	public StartingStuffGUI() {
		super("Stuff de départ", 3, "Stuff de départ", "Définir le stuff de départ des joueurs", Material.WOOD_SWORD, GUIRegister.main);
	}

	@Override
	public void onPartyStart() {
		if(items.length > 0)
			for(UUID uuid : PlayerInGame.playerInGame) {
				for(ItemStack i : items)
					if(i != null && i.getType() != Material.BARRIER)
						Bukkit.getPlayer(uuid).getInventory().addItem(i);
			}
	}
	
	@EventHandler()
	public void onClicks(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(inv.getName())) {
			Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
				items = e.getInventory().getContents();
			}, 1);	
		}
	}
	
	@Override
	public void refresh(Player p) {
		this.inv.setContents(items);
		addReturnButton();
		super.refresh(p);
	}

}
