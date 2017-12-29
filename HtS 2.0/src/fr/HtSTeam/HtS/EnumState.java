package fr.HtSTeam.HtS;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;

public enum EnumState implements Listener {
	
	WAIT,
	RUNNING,
	FINISHING;
	
	private static EnumState state = EnumState.RUNNING;
	
	public static void setState(EnumState state) {
		EnumState.state = state;
		if(state.equals(EnumState.WAIT)) {
			for (World world : Bukkit.getWorlds())
				world.setPVP(false);
		}
	}
	
	public static EnumState getState() { return state; }
	
}

