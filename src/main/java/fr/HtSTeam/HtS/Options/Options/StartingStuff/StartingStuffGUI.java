package fr.HtSTeam.HtS.Options.Options.StartingStuff;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;

public class StartingStuffGUI extends GUIBuilder implements StartTrigger, OptionIO {
	
	ItemStack[] items = new ItemStack[0];

	public StartingStuffGUI() {
		super("Stuff de départ", 3, "Stuff de départ", "Définir le stuff de départ des joueurs", Material.WOODEN_SWORD, GUIRegister.main);
		addToList();
	}

	@Override
	public void onPartyStart() {
//		if(items.length > 0)
//			for(UUID uuid : PlayerInGame.playerInGame) {
//				for(ItemStack i : items)
//					if(i != null && i.getType() != Material.BARRIER)
//						Bukkit.getPlayer(uuid).getInventory().addItem(i);
//			}
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
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode((String) o));
	        BukkitObjectInputStream data2;
			data2 = new BukkitObjectInputStream(stream);
			ItemStack is;
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			try {
				while(true) {
					is = (ItemStack)data2.readObject();
					list.add(is);
				}
			} catch(EOFException e) {}
			finally {
				data2.close();
			}
			items = list.toArray(new ItemStack[list.size()]);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<String> save() {
		if(items.length == 0)
			return null;
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
		return getName();
	}	
}
