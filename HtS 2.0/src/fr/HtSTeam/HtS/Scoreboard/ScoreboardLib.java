package fr.HtSTeam.HtS.Scoreboard;


import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScoreboardLib extends JavaPlugin {

    private static Plugin instance;

    public static Plugin getPluginInstance() {
        return instance;
    }

    public static void setPluginInstance(Plugin instance) {
        if (ScoreboardLib.instance != null) return;
        ScoreboardLib.instance = instance;
    }

    public static SimpleScoreboard createScoreboard(Player holder) {
        return  new SimpleScoreboard(holder);
    }

    @Override
    public void onEnable() {
        setPluginInstance(this);
    }

}