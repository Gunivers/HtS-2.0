package fr.HtSTeam.HtS.Scoreboard.Scoreboard;

import java.util.List;

import org.bukkit.entity.Player;

public interface ScoreboardHandler {

    String getTitle(Player player);
    List<Entry> getEntries(Player player);

}