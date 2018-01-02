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
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Entry;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.EntryBuilder;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Scoreboard;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardHandler;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardLib;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScrollableString;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.Strings;
import fr.HtSTeam.HtS.Teams.TeamManager;

public class ScoreBoard {
	
	public static ArrayList<String> display = new ArrayList<String>();
	public static Map<UUID, Scoreboard> scoreboards = new HashMap<UUID, Scoreboard>();
	private final static ScrollableString scroll = new ScrollableString(Strings.format("§4JEU EN PAUSE"), 40, 0);
		
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
			return new EntryBuilder().next("§6Joueur:").next(Integer.toString(PlayerInGame.playerInGame.size())).next("§6Kills:").next(Integer.toString(p.getStatistic(Statistic.PLAYER_KILLS))).next("§6Timer:").next(Main.timer.getTimeFormat()).next("§6Bordure:").next(OptionsRegister.borderOption.getValue() + "x" + OptionsRegister.borderOption.getValue()).build();
		
		EntryBuilder builder = new EntryBuilder();
		
		for(int i = 0; i < display.size(); i++) {
			switch(display.get(i)) {
				case "PlayerScoreboardOption":
					builder.next("§6Joueurs:").next(Integer.toString(PlayerInGame.playerInGame.size()));
					if (TeamManager.teamList.size() != 0)
						builder.next("§6Equipes:").next(Integer.toString(TeamManager.teamList.size()));
					break;
				case "KilledScoreboardOption":
					builder.next("§6Kills:").next(Integer.toString(p.getStatistic(Statistic.PLAYER_KILLS)));
					break;
				case "TimerScoreboardOption":
					builder.next("§6Timer:").next(Main.timer.getTimeFormat());
					if (Main.gamemode.gamemodeTotring().equals("Fallen Kingdom"))
						builder.next("§6Days:").next(Integer.toString((int) Main.timer.getTimerInMinute() / 20));
					break;
				case "BorderScoreboardOption":
					builder.next("§6Bordure:").next(OptionsRegister.borderOption.getValue() + "×" + OptionsRegister.borderOption.getValue());
					break;
				case "AddBlankScoreboardOption":
					builder.blank();
					break;
			}
		}
		
		if (PlayStopCommands.pause)
			builder.blank().blank().next("		" + scroll.next());
		
		return builder.build();
	}
}
