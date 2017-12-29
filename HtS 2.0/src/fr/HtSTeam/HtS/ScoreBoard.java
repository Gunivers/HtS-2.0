package fr.HtSTeam.HtS;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.TeamManager;
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
				return Main.HTSNAME;
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
			return new EntryBuilder().next("§4Joueur:").next(getPlayerAlive()).next("§4Tuer:").next(getPlayerKilled()).next("§4Timer:").next(getTime()).next("§4Bordure:").next(OptionsRegister.borderOption.getValue() + "x" + OptionsRegister.borderOption.getValue()).build();
		
		EntryBuilder builder = new EntryBuilder();
		
		for(int i = 0; i < display.size(); i++) {
			switch(display.get(i)) {
				case "PlayerScoreboardOption":
					builder.next("§4Joueur:").next(getPlayerAlive());
					if (TeamManager.teamList.size() != 0)
						builder.next("§4Equipe:").next(Integer.toString(TeamManager.teamList.size()));
					break;
				case "KilledScoreboardOption":
					builder.next("§4Tuer:").next(getPlayerKilled());
					break;
				case "TimerScoreboardOption":
					builder.next("§4Timer:").next(getTime());
					break;
				case "BorderScoreboardOption":
					builder.next("§4Bordure:").next(OptionsRegister.borderOption.getValue() + "x" + OptionsRegister.borderOption.getValue());
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
