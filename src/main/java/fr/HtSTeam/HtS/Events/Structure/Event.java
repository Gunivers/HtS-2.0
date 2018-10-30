package fr.HtSTeam.HtS.Events.Structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Player.Player;

public class Event implements Listener {
	
	private HashMap<Class<?>, ArrayList<Method>> eventMethods = new HashMap<Class<?>, ArrayList<Method>>();

	public Event() {
		Main.LOGGER.logInfo("[Events] Registering events...");
		ArrayList<Method> annoted_methods = new ArrayList<Method>(new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("fr.HtSTeam.HtS")).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(EventHandler.class));
		annoted_methods.removeIf(m -> m.getParameterCount() != 1);
		HashSet<Class<?>> clazzes = new HashSet<Class<?>>();
		annoted_methods.forEach(m -> { clazzes.add(m.getParameterTypes()[0]); });
		clazzes.forEach(clazz -> {
			ArrayList<Method> methods = new ArrayList<Method>();
			annoted_methods.forEach(m -> {
				if (m.getParameterTypes()[0].equals(clazz))
					methods.add(m);
			});
			methods.sort((o1, o2) -> o1.getAnnotation(EventHandler.class).value()
					.compareTo(o2.getAnnotation(EventHandler.class).value()));
			eventMethods.put(clazz, methods);
		});
		Main.LOGGER.logInfo("[Events] " + annoted_methods.size() + " events registered!");
	}
		
	private void invoke(Class<?> clazz, Object event, Player player) {
		if (!eventMethods.containsKey(clazz))
			return;
		eventMethods.get(clazz).forEach(m -> { try {
			m.invoke(null, event, player);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} });
	}
	
	// Events
	
	@org.bukkit.event.EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) { org.bukkit.entity.Player p = null; if (event.getPlayer() != null) p = event.getPlayer(); Bukkit.broadcastMessage(event.getJoinMessage()); event.setJoinMessage(null); invoke(event.getClass(), event, Player.instance(p)); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) { org.bukkit.entity.Player p = null; if (event.getPlayer() != null) p = event.getPlayer(); String msg = event.getQuitMessage(); event.setQuitMessage(null); invoke(event.getClass(), event, Player.instance(p)); Bukkit.broadcastMessage(msg); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) { org.bukkit.entity.Player p = null; if (event.getPlayer() != null) p = (org.bukkit.entity.Player) event.getPlayer(); invoke(event.getClass(), event, Player.instance(p)); }
	
	@org.bukkit.event.EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) { org.bukkit.entity.Player p = null; if (event.getEntity() != null && event.getEntity() instanceof org.bukkit.entity.Player) p = ((org.bukkit.entity.Player) event).getPlayer(); invoke(event.getClass(), event, Player.instance(p)); }
	
	@org.bukkit.event.EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) { org.bukkit.entity.Player p = null; if (event.getEntity() != null && event.getEntity() instanceof org.bukkit.entity.Player) p = (org.bukkit.entity.Player) event.getEntity(); invoke(event.getClass(), event, Player.instance(p)); }
	
	@org.bukkit.event.EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) { org.bukkit.entity.Player p = null; if (event.getPlayer() != null) p = event.getPlayer(); invoke(event.getClass(), event, Player.instance(p)); }
	
	@org.bukkit.event.EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) { org.bukkit.entity.Player p = null; if (event.getPlayer() != null) p = event.getPlayer(); invoke(event.getClass(), event, Player.instance(p)); }
	
	@org.bukkit.event.EventHandler
	public void onBlockPlaceEvent(PlayerChangedWorldEvent event) { org.bukkit.entity.Player p = null; if (event.getPlayer() != null) p = event.getPlayer(); invoke(event.getClass(), event, Player.instance(p)); }
	
	@org.bukkit.event.EventHandler
	public void onBlockPlaceEvent(PlayerDeathEvent event) { org.bukkit.entity.Player p = null; if (event.getEntity() != null) p = event.getEntity(); invoke(event.getClass(), event, Player.instance(p)); }

}