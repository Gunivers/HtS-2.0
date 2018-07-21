package fr.HtSTeam.HtS.Options.Options.StartingStuff;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Utils.OptionIO;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public class StartingStuffGUI extends GUIBuilder implements StartTrigger, OptionIO {
	
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

	@Override
	public void load(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> save() {
		try {
			 ByteArrayOutputStream str = new ByteArrayOutputStream();	
				BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
				for(ItemStack i : items)
					data.writeObject(i);
				data.close();
				ArrayList<String> elements = new ArrayList<String>();
				elements.add(Base64.getEncoder().encodeToString(str.toByteArray()));
				return elements;
				
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public String getId() {
		return "StartingStuff";
	}	
}
