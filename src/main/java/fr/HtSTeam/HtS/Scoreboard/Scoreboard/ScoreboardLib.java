package fr.HtSTeam.HtS.Scoreboard.Scoreboard;


import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class ScoreboardLib{

    private static Plugin instance;

    public static Plugin getPluginInstance() {
        return instance;
    }

    public static void setPluginInstance(Plugin instance) {
        if (ScoreboardLib.instance != null) return;
        ScoreboardLib.instance = instance;
    }

    public static Scoreboard createScoreboard(Player holder) {
        return (Scoreboard) new SimpleScoreboard(holder);
    }
}