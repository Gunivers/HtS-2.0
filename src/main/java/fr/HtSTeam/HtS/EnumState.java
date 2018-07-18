package fr.HtSTeam.HtS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;

import fr.HtSTeam.HtS.Options.Structure.IconBuilder;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public enum EnumState implements Listener {
	
	WAIT,
	RUNNING,
	FINISHING;
	
	private static EnumState state = EnumState.WAIT;
	
	public static void setState(EnumState state) {
		EnumState.state = state;
		if(state.equals(EnumState.WAIT)) {
			for (World world : Bukkit.getWorlds())
				world.setPVP(false);
		} else if(state.equals(EnumState.RUNNING)) {
			List<IconBuilder<?>> keyset = new ArrayList<IconBuilder<?>>(IconBuilder.optionsList.keySet());
			for(int i = 0; i < keyset.size(); i++)
				if(Arrays.asList(keyset.get(i).getClass().getInterfaces()).contains(StartTrigger.class))
					((StartTrigger) keyset.get(i)).onPartyStart();
			 Main.gamemode.initialisation();
		}
	}
	
	public static EnumState getState() { return state; }
	
}

