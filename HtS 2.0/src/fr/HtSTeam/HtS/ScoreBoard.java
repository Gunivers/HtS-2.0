package fr.HtSTeam.HtS;

import java.util.List;

import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Scoreboard.Entry;
import fr.HtSTeam.HtS.Scoreboard.EntryBuilder;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard;
import fr.HtSTeam.HtS.Scoreboard.ScoreboardHandler;
import fr.HtSTeam.HtS.Scoreboard.ScoreboardLib;

public class ScoreBoard {

	public static void send(Player player, Main main) {

		Scoreboard scoreboard = ScoreboardLib.createScoreboard(player).setHandler(new ScoreboardHandler() {

			@Override
			public String getTitle(Player player) {
				return "Title Or Nothing";
			}

			@Override
			public List<Entry> getEntries(Player player) {			
					return new EntryBuilder().next("§r                    ")
							.next("Line 1")
							.blank()
							.next("Line 3")
							.build();
				
			}
		}).setUpdateInterval(2l);
		scoreboard.activate();
	}
}
