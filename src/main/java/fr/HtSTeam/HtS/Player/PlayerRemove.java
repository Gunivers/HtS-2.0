package fr.HtSTeam.HtS.Player;

import java.util.ArrayList;
import java.util.UUID;

public interface PlayerRemove {
	
	public static ArrayList<PlayerRemove> remove = new ArrayList<PlayerRemove>();
	public static ArrayList<PlayerRemove> last = new ArrayList<PlayerRemove>();

	public void removePlayer(UUID uuid, String name);
	
	public default void addToList() {
		remove.add(this);
	}
	
	public default void hasLast() {
		last.add(this);
	}
	
	public static void addLast() { //Appelé dans EnumState
		remove.addAll(last); 
	}
	
}
