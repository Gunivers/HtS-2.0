package fr.HtSTeam.HtS.Events.Structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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
	
	/**
	 * Registers every annoted methods into Events.
	 */
	public Event() {
		Main.LOGGER.logInfo("[Events] Registering events...");
		ArrayList<Method> annoted_methods = new ArrayList<Method>(new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("fr.HtSTeam.HtS")).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(EventHandler.class));
		annoted_methods.removeIf(m -> m.getParameterCount() != 2);
		HashSet<Class<?>> clazzes = new HashSet<Class<?>>();
		annoted_methods.forEach(m -> { clazzes.add(m.getParameterTypes()[0]); Main.LOGGER.logInfo("[Events] > " + m.getParameterTypes()[0].getName().substring(m.getParameterTypes()[0].getName().lastIndexOf('.') + 1) + "    ...    " + m.getDeclaringClass().getName() + "." + m.getName()); });
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
	
	/**
	 * Dispatches the event to concerned methods.
	 * @param clazz
	 * @param event
	 * @param player
	 */
	private void invoke(Class<?> clazz, Object event, Player player) {
		if (!eventMethods.containsKey(clazz))
			return;
		eventMethods.get(clazz).forEach(m -> { try {
			m.invoke(null, event, player);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} });
	}
	
	/**
	 * Returns the {@link Player Player(HtS)} if the entity is a {@link org.bukkit.entity.Player Player(Bukkit)} else null.
	 * @param e
	 * @return
	 */
	private Player getPlayer(Entity e) {
		if (e instanceof org.bukkit.entity.Player)
			return Player.instance((org.bukkit.entity.Player) e);
		return null;
	}
	
	// Events
	
	@org.bukkit.event.EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) { Bukkit.broadcastMessage(event.getJoinMessage()); event.setJoinMessage(null);	invoke(event.getClass(), event, getPlayer(event.getPlayer())); }

	@org.bukkit.event.EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) { String msg = event.getQuitMessage(); event.setQuitMessage(null);	invoke(event.getClass(), event, getPlayer(event.getPlayer())); Bukkit.broadcastMessage(msg); }

	@org.bukkit.event.EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer()));}

	@org.bukkit.event.EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity()));	}

	@org.bukkit.event.EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }

	@org.bukkit.event.EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }

	@org.bukkit.event.EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }

	@org.bukkit.event.EventHandler
	public void onBlockPlaceEvent(PlayerChangedWorldEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }

	@org.bukkit.event.EventHandler
	public void onBlockPlaceEvent(PlayerDeathEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }

	@org.bukkit.event.EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
}