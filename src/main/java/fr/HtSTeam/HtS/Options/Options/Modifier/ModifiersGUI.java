package fr.HtSTeam.HtS.Options.Options.Modifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;
import fr.HtSTeam.HtS.Options.Structure.IconBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public class ModifiersGUI extends GUIBuilder implements StartTrigger, CommandExecutor {
	
	private Map<Player, CustomGUI> customInventory = new HashMap<Player, CustomGUI>();
	private boolean active = false;

	public ModifiersGUI() {
		super("Modifiers", 1, "Modifiers", "Activer/Désactiver des items modifiés", Material.END_CRYSTAL, GUIRegister.main);
		Main.plugin.getCommand("gui").setExecutor(this);
	}
	
	@Override
	public void event(Player p) {
		super.event(p);
	}
	
	@Override
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		for(Entry<ItemStackBuilder, IconBuilder<?>> ism : guiContent.entrySet()) {
			if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.BARRIER) && ism.getKey().equals(e.getCurrentItem())) {
				e.setCancelled(true);
				ism.getValue().event((Player) e.getWhoClicked());
			} else if(e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.BARRIER)) {
				e.setCancelled(true);
				ism.getValue().event((Player) e.getWhoClicked());
			}
		}
	}

	@Override
	public void onPartyStart() {
		for(Entry<ItemStackBuilder, IconBuilder<?>> entry : guiContent.entrySet())
			if(entry.getValue().getValue() != null && entry.getValue().getValue().equals("Activé")) {
				active = true;
				continue;
			}
		if(!active) return;
//		for(UUID p : PlayerInGame.playerInGame)
//			customInventory.put(Player.instance(p), new CustomGUI());
	}
	
	public CustomGUI getInventory(Player p) {
		if(customInventory.containsKey(p)) return customInventory.get(p);
		return null;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (active && sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("gui") && sender.hasPermission("gui.use")) {
				getInventory(p).open(p);
				return true;
			}
			
		} else if(!active) {
			sender.sendMessage("§4Erreur ! Partie non lancée ou option non activée.");
			return true;
		}
		
		return false;
	}
}
