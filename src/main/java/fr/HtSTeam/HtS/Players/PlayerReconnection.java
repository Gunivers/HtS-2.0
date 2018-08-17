package fr.HtSTeam.HtS.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerReconnection implements Listener {

	private HashMap<UUID, ArrayList<Object>> waitingList = new HashMap<UUID, ArrayList<Object>>();
	
	public void onPlayerReconnect(PlayerJoinEvent e) {
		for(Entry<UUID, ArrayList<Object>> entry : waitingList.entrySet()) {
			for(Object o : entry.getValue()) {
				
			}
		}
	}
}
