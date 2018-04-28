package fr.HtSTeam.HtS.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Commands.PlayStopCommands;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Entry;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.EntryBuilder;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.HighlightedString;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Scoreboard;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardHandler;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardLib;
import fr.HtSTeam.HtS.Teams.TeamBuilder;

public class ScoreBoard {
	
	public static ArrayList<String> display = new ArrayList<String>();
	public static Map<UUID, Scoreboard> scoreboards = new HashMap<UUID, Scoreboard>();
	private final static HighlightedString highlighted = new HighlightedString("JEU EN PAUSE", "&4", "&c");
	
	public static void send(Player player) {

		Scoreboard scoreboard = ScoreboardLib.createScoreboard(player).setHandler(new ScoreboardHandler() {
			@Override
			public String getTitle(Player player) {
				return Main.HTSNAME;
			}

			@Override
			public List<Entry> getEntries(Player player) {			
					return getBuild(player);
			}

		}).setUpdateInterval(2l);
		scoreboard.activate();
		scoreboards.put(player.getUniqueId(), scoreboard);
	}
	
	private static List<Entry> getBuild(Player p) {
		if(display.size() == 0)
			return new EntryBuilder().next("§6Joueur :").next(Integer.toString(PlayerInGame.playerInGame.size())).next("§6Kills :").next(Integer.toString(p.getStatistic(Statistic.PLAYER_KILLS))).next("§6Timer :").next(Main.timer.getTimeFormat()).next("§6Bordure :").next(OptionRegister.borderOption.getValue() + "x" + OptionRegister.borderOption.getValue()).build();
		
		EntryBuilder builder = new EntryBuilder();
		
		for(int i = 0; i < display.size(); i++) {
			switch(display.get(i)) {
				case "PlayerScoreboardOption":
					builder.next("§6Joueurs :").next(Integer.toString(PlayerInGame.playerInGame.size()));
					if (TeamBuilder.teamList.size() != 0)
						builder.next("§6Equipes :").next(Integer.toString(TeamBuilder.teamList.size()));
					break;
				case "KilledScoreboardOption":
					builder.next("§6Kill :").next(Integer.toString(p.getStatistic(Statistic.PLAYER_KILLS)));
					break;
				case "TimerScoreboardOption":
					builder.next("§6Timer :").next(Main.timer.getTimeFormat());
					if (Main.gamemode.gamemodeToString().equals("Fallen Kingdom"))
						builder.next("§6Jours :").next(Integer.toString((int) Main.timer.getTimerInMinute() / 20));
					break;
				case "BorderScoreboardOption":
					builder.next("§6Bordure :").next(OptionRegister.borderOption.getValue() + "×" + OptionRegister.borderOption.getValue());
					break;
				case "AddBlankScoreboardOption":
					builder.blank();
					break;
			}
		}
		
		if (PlayStopCommands.pause)
			builder.blank().blank().next(highlighted.next());
		
		return builder.build();
	}
}
