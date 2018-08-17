package fr.HtSTeam.HtS.Utils;

import java.util.ArrayList;
import java.util.UUID;

public interface PlayerRemove {
	
	public static ArrayList<PlayerRemove> remove = new ArrayList<PlayerRemove>();
	public static PlayerRemove last = null;

	public void removePlayer(UUID uuid, String name);
	
	public default void addToList() {
		remove.add(this);
	}
	
	public default void hasLast() {
		//last = this;
	}
	
}
