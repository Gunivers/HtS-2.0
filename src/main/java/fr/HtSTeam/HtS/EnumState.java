package fr.HtSTeam.HtS;

import java.sql.SQLException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;

import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.IconBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Players.PlayerRemove;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

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
			IconBuilder.optionsList.keySet().forEach(key -> { if(Arrays.asList(key.getClass().getInterfaces()).contains(StartTrigger.class)) ((StartTrigger) key).onPartyStart(); });
			Main.gamemode.initialisation();
			PlayerRemove.addLast();
			StatisticHandler.init();
		} else if (state.equals(EnumState.FINISHING)) {
			IconBuilder.optionsList.keySet().forEach(key -> { if(Arrays.asList(key.getClass().getInterfaces()).contains(EndTrigger.class)) ((EndTrigger) key).onPartyEnd(); });
			ScoreBoard.scoreboards.forEach((uuid, sb) -> { sb.deactivate(); });
			StatisticHandler.display();
			try {
				StatisticHandler.save();
			} catch (SQLException e) { e.printStackTrace();	}
		}
	}
	
	public static EnumState getState() { return state; }
	
}
