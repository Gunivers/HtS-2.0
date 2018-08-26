package fr.HtSTeam.HtS.Events.Strutcture;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class Event implements Listener {
	
	private ArrayList<Method> _field_3124w9;

	public Event() {
		_field_3124w9 = new ArrayList<Method>(new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(EventHandler.class));
		_field_3124w9.removeIf(m -> m.getParameterCount() != 1);
	}
	
	private ArrayList<Method> methods() { return _field_3124w9; }
	
	private ArrayList<Method> sort(ArrayList<Method> methods) {
		methods.sort((o1, o2) -> o1.getAnnotation(EventHandler.class).value().compareTo(o2.getAnnotation(EventHandler.class).value()));
		return methods;
	}
	
	@org.bukkit.event.EventHandler
	public void onJoin(PlayerJoinEvent event) {
		ArrayList<Method> methods = new ArrayList<Method>();
		for (Method m : methods())
			if (m.getTypeParameters()[0] instanceof PlayerJoinEvent)
				methods.add(m);
		methods = sort(methods);
		
		methods.forEach(m -> { try {
			m.invoke(null, event);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} });
	}
}