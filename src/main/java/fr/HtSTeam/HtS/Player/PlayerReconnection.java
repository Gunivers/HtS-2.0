package fr.HtSTeam.HtS.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.HtSTeam.HtS.Options.Structure.Annotation.AwaitingPlayer;

public class PlayerReconnection implements Listener {

	private static HashMap<UUID, ArrayList<Object>> waitingList = new HashMap<UUID, ArrayList<Object>>();
	
	@EventHandler
	public void onPlayerReconnect(PlayerJoinEvent e) {
		Bukkit.broadcastMessage(e.getJoinMessage());
		e.setJoinMessage(null);
		if(waitingList.get(e.getPlayer().getUniqueId()) == null) return;
		for (Object o : waitingList.get(e.getPlayer().getUniqueId())) {
			for (Method m : o.getClass().getMethods())
				if (m.isAnnotationPresent(AwaitingPlayer.class))
					try {
						m.invoke(o, e.getPlayer().getUniqueId());
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) { ex.printStackTrace(); }
		}
	}
	
	@SuppressWarnings("serial")
	public static void add(UUID uuid, Object o) {
		if (waitingList.containsKey(uuid)) {
			ArrayList<Object> array = waitingList.get(uuid);
			array.add(o);
			waitingList.put(uuid, array);
		} else
			waitingList.put(uuid, new ArrayList<Object>() {{ add(o); }});
	}
	
	public static void remove(UUID uuid) {
		waitingList.remove(uuid);
	}
}
