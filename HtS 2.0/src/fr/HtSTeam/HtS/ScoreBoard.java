package fr.HtSTeam.HtS;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Scoreboard.Entry;
import fr.HtSTeam.HtS.Scoreboard.EntryBuilder;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard;
import fr.HtSTeam.HtS.Scoreboard.ScoreboardHandler;
import fr.HtSTeam.HtS.Scoreboard.ScoreboardLib;

public class ScoreBoard {
	
	public static ArrayList<String> display = new ArrayList<String>(); 
	
	public static void send(Player player) {

		Scoreboard scoreboard = ScoreboardLib.createScoreboard(player).setHandler(new ScoreboardHandler() {

			@Override
			public String getTitle(Player player) {
				return Main.hts_name;
			}

			@Override
			public List<Entry> getEntries(Player player) {			
					return getBuild();
			}

		}).setUpdateInterval(2l);
		scoreboard.activate();
	}
	
	private static List<Entry> getBuild() {
		if(display == null || display.size() == 0)
			return new EntryBuilder().next("§4SCOREBOARD").next("§4NOT").next("§4INITIALIZED").build();
		
		EntryBuilder builder = new EntryBuilder();
		
		for(int i = 0; i < display.size(); i++) {
			switch(display.get(i)) {
				case "PlayerScoreboardOption":
					builder.next(getPlayerAlive() + " joueurs en vie");
					break;
				case "KilledScoreboardOption":
					builder.next(getPlayerKilled() + " joueurs tués");
					break;
				case "TimerScoreboardOption":
					builder.next(getTime());
					break;
				case "BorderScoreboardOption":
					builder.next(OptionsRegister.borderoption.getValue() + "x" + OptionsRegister.borderoption.getValue());
					break;
				case "AddBlankScoreboardOption":
					builder.blank();
					break;
			}
		}
		
		return builder.build();
	}

	private static String getPlayerAlive() {
		return "3";
	}
	
	private static String getPlayerKilled() {
		return "6";
	}
	
	private static String getTime() {
		return "00:00:00";
	}

}
