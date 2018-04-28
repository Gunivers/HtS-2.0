package fr.HtSTeam.HtS.Options.Options.Modifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public class ModifiersGUI extends GUIBuilder implements StartTrigger, CommandExecutor {
	
	private Map<Player, CustomGUI> customInventory = new HashMap<Player, CustomGUI>();
	private boolean active = false;

	public ModifiersGUI() {
		super("Modifiers", 1, "Modifiers", "Activer/Désactiver des items modifiés", Material.END_CRYSTAL, OptionRegister.main);
		Main.plugin.getCommand("gui").setExecutor(this);
	}
	
	@Override
	public void event(Player p) {
		super.event(p);
	}
	
	@Override
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		for(Entry<ItemStackBuilder, OptionBuilder> ism : guiContent.entrySet()) {
			if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.BARRIER) && ism.getKey().equals(e.getCurrentItem())) {
				e.setCancelled(true);
				ism.getValue().event((Player) e.getWhoClicked());
			} else if(e.getCurrentItem().getType().equals(Material.BARRIER)) {
				e.setCancelled(true);
				ism.getValue().event((Player) e.getWhoClicked());
			}
		}
	}

	@Override
	public void onPartyStart() {
		for(Entry<ItemStackBuilder, OptionBuilder> entry : guiContent.entrySet())
			if(entry.getValue().getValue() != null && entry.getValue().getValue().equals("Activé")) {
				active = true;
				continue;
			}
		if(!active) return;
		for(UUID p : PlayerInGame.playerInGame)
			customInventory.put(Bukkit.getPlayer(p), new CustomGUI());
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
