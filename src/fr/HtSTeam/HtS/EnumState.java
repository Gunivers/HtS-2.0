package fr.HtSTeam.HtS;

import java.util.Arrays;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;

import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
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
			for(Entry<OptionBuilder, Object> om : OptionBuilder.optionsList.entrySet()) {
				if(Arrays.asList(om.getKey().getClass().getInterfaces()).contains(StartTrigger.class))
					((StartTrigger) om.getKey()).onPartyStart();
			}
			 Main.gamemode.initialisation();
		}
	}
	
	public static EnumState getState() { return state; }
	
}

