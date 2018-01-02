package fr.HtSTeam.HtS.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Entry;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.EntryBuilder;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Scoreboard;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardHandler;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardLib;
import fr.HtSTeam.HtS.Teams.TeamManager;

public class ScoreBoard {
	
	public static ArrayList<String> display = new ArrayList<String>();
	public static Map<UUID, Scoreboard> scoreboards = new HashMap<UUID, Scoreboard>();
	
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
		scoreboards.put(player.getUniqueId(), scoreboard);
	}
	
	private static List<Entry> getBuild() {
		if(display.size() == 0)
			return new EntryBuilder().next("§6Joueur:").next(Integer.toString(PlayerInGame.playerInGame.size())).next("§6Tuer:").next(getPlayerKilled()).next("§6Timer:").next(Main.timer.getTimeFormat()).next("§6Bordure:").next(OptionsRegister.borderOption.getValue() + "x" + OptionsRegister.borderOption.getValue()).build();
		
		EntryBuilder builder = new EntryBuilder();
		
		for(int i = 0; i < display.size(); i++) {
			switch(display.get(i)) {
				case "PlayerScoreboardOption":
					builder.next("§6Joueurs:").next(Integer.toString(PlayerInGame.playerInGame.size()));
					if (TeamManager.teamList.size() != 0)
						builder.next("§6Equipes:").next(Integer.toString(TeamManager.teamList.size()));
					break;
				case "KilledScoreboardOption":
					builder.next("§6Tuers:").next(getPlayerKilled());
					break;
				case "TimerScoreboardOption":
					builder.next("§6Timer:").next(Main.timer.getTimeFormat());
					break;
				case "BorderScoreboardOption":
					builder.next("§6Bordure:").next(OptionsRegister.borderOption.getValue() + "×" + OptionsRegister.borderOption.getValue());
					break;
				case "AddBlankScoreboardOption":
					builder.blank();
					break;
			}
		}
		
		return builder.build();
	}
	
	private static String getPlayerKilled() {
		return "6";
	}
}
