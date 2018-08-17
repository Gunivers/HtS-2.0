package fr.HtSTeam.HtS.Players;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.HtSTeam.HtS.Options.Structure.Annotation.AwaitingPlayer;

public class PlayerReconnection implements Listener {

	private HashMap<UUID, ArrayList<Object>> waitingList = new HashMap<UUID, ArrayList<Object>>();
	
	@EventHandler
	public void onPlayerReconnect(PlayerJoinEvent e) {
		for (Object o : waitingList.get(e.getPlayer().getUniqueId())) {
			if (!(o instanceof Class))
				continue;
			for (Method m : o.getClass().getMethods())
				if (m.isAnnotationPresent(AwaitingPlayer.class))
					try {
						m.invoke(o, e.getPlayer().getUniqueId());
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) { ex.printStackTrace(); }
		}
	}
}
