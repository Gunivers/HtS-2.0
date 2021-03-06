package fr.HtSTeam.HtS.Scoreboard.Scoreboard;

import org.bukkit.entity.Player;

public interface Scoreboard {

    void activate();

    void deactivate();

    boolean isActivated();

    ScoreboardHandler getHandler();

    Scoreboard setHandler(ScoreboardHandler handler);

    long getUpdateInterval();

    Scoreboard setUpdateInterval(long updateInterval);

    Player getHolder();

}