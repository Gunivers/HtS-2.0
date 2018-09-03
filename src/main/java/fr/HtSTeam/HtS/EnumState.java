package fr.HtSTeam.HtS;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;

import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.IconBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Players.PlayerRemove;

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
			IconBuilder.optionsList.keySet().stream().filter(key -> key instanceof StartTrigger).forEach(key -> ((StartTrigger) key).onPartyStart());
			Main.gamemode.initialisation();
			PlayerRemove.addLast();
			StatisticHandler.init();
		} else if (state.equals(EnumState.FINISHING)) {
			IconBuilder.optionsList.keySet().stream().filter(key -> key instanceof EndTrigger).forEach(key -> ((EndTrigger) key).onPartyEnd());
			StatisticHandler.display();
			try {
				StatisticHandler.save();
			} catch (SQLException e) { e.printStackTrace();	}
		}
	}
	
	public static EnumState getState() { return state; }
	
}
