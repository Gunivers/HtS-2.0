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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class Event implements Listener {
	
	private ArrayList<Method> annoted_methods;
	private HashMap<Class<?>, ArrayList<Method>> eventMethods = new HashMap<Class<?>, ArrayList<Method>>();

	public Event() {
		System.out.println("Registering events...");
		annoted_methods = new ArrayList<Method>(new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("fr.HtSTeam.HtS")).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(EventHandler.class));
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
		System.out.println(annoted_methods.size() + " events registered!");
	}
		
	private void invoke(Class<?> clazz, Object event) {
		if (!eventMethods.containsKey(clazz))
			return;
		eventMethods.get(clazz).forEach(m -> { try {
			m.invoke(null, event);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} });
	}
	
	// Events
	
	@org.bukkit.event.EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) { Bukkit.broadcastMessage(event.getJoinMessage()); event.setJoinMessage(null); invoke(event.getClass(), event); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) { String msg = event.getQuitMessage(); event.setQuitMessage(null); invoke(event.getClass(), event); Bukkit.broadcastMessage(msg); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) { invoke(event.getClass(), event); }
	
	@org.bukkit.event.EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) { invoke(event.getClass(), event); }
	
	@org.bukkit.event.EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) { invoke(event.getClass(), event); }
	
	@org.bukkit.event.EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) { invoke(event.getClass(), event); }
	
	@org.bukkit.event.EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) { invoke(event.getClass(), event); }

}