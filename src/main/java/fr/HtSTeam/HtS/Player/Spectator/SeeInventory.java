package fr.HtSTeam.HtS.Player.Spectator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Player.PlayerManager;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public class SeeInventory implements Listener {
	
	public Map<UUID, Inventory> inventories = new HashMap<UUID, Inventory>();
	UUID uuid = null;
	
	@EventHandler
	public void onInteractWithInventory(InventoryClickEvent  e) {
		if(e.getWhoClicked() instanceof Player) {
			Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
				refresh((Player) e.getWhoClicked());
			}, 1);	
		}
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent  e) {
		if(e.getWhoClicked() instanceof Player) {
			Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
				refresh((Player) e.getWhoClicked());
			}, 1);
		}
	}
	
	@EventHandler
	public void onLevelChange(PlayerLevelChangeEvent  e) {
		Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
			refresh(e.getPlayer());
		}, 1);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player)
			Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
				refresh((Player) e.getEntity());
			}, 1);
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player)
			Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
				refresh((Player) e.getEntity());
			}, 1);
	}
	
	@EventHandler
	public void onRegainHeal(EntityRegainHealthEvent e) {
		if(e.getEntity() instanceof Player)
			Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
				refresh((Player) e.getEntity());
			}, 1);
	}
	
	@EventHandler
	public void onPickUp(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player)
			Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
				refresh((Player) e.getEntity());
			}, 1);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
			refresh((Player) e.getEntity());
		}, 1);
	}
	
	
	
	
	public void refresh(Player p) {
		for(Entry<UUID, Inventory> entry : inventories.entrySet()) {
			if(entry.getValue().getName().equals(p.getName()) && PlayerManager.isConnected(p.getUniqueId())) {
				uuid = entry.getKey();
			}
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(uuid != null) {
					inventories.remove(uuid);
					createInventory(Bukkit.getPlayer(uuid), p);
					uuid = null;
				}
			}
		}.runTaskLater(Main.plugin, 1);
	}
	
	
	
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR && inventories.containsValue(e.getInventory())) {
			inventories.remove(e.getPlayer().getUniqueId());
		}
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) { 
			if(e.getRightClicked() instanceof Player) {
				Player p = (Player)e.getRightClicked();
				if(p.getGameMode() == GameMode.SURVIVAL) {
					createInventory(e.getPlayer(), p);	
				}
			}
		}
	}
	
	public void createInventory(Player clicker, Player clicked) {
		
		Inventory inv = Bukkit.createInventory(null, 54, clicked.getName());
		int j = 18;
		for(int i = 9; i <= 35; i++) {
			inv.setItem(j, clicked.getInventory().getItem(i));
			j++;
		}
		j = 45;
		for(int i = 0; i <= 8; i++) {
			inv.setItem(j, clicked.getInventory().getItem(i));
			j++;
		}
		for(j = 9; j <= 17; j++) {
			inv.setItem(j, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
		}
		
		inv.setItem(0, clicked.getInventory().getHelmet());
		inv.setItem(1, clicked.getInventory().getChestplate());
		inv.setItem(2, clicked.getInventory().getLeggings());
		inv.setItem(3, clicked.getInventory().getBoots());
		
		inv.setItem(4, clicked.getInventory().getItemInOffHand());
		
		ItemStackBuilder xp = new ItemStackBuilder(Material.EXPERIENCE_BOTTLE, clicked.getLevel(), "Niveaux", null);
		ItemStackBuilder health = new ItemStackBuilder(Material.REDSTONE, (int) clicked.getHealth(), "Vie", null);
		ItemStackBuilder food = new ItemStackBuilder(Material.COOKED_BEEF, clicked.getFoodLevel(), "Faim", null);
		inv.setItem(6, xp);
		inv.setItem(7, health);
		inv.setItem(8, food);
		
		clicker.openInventory(inv);
		
		inventories.put(clicker.getUniqueId(), inv);
		
	}

}
