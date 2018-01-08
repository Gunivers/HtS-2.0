package fr.HtSTeam.HtS.Commands;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;
import fr.HtSTeam.HtS.Utils.JSON;

public class StartCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main main;
	
	public StartCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			
			if (cmd.getName().equalsIgnoreCase("start") && sender.hasPermission("start.use") && EnumState.getState().equals(EnumState.WAIT)) {
				for(Entry<OptionBuilder, Object> entry : OptionBuilder.optionsList.entrySet()) {
					OptionBuilder key = entry.getKey();
					Object value = entry.getValue();
					if(value != null && key.getDefaultValue() != null  && !key.getDefaultValue().equals(value))
						p.sendMessage(key.getName() + " : §4" + value.toString());
				}
				JSON.sendJsonRunCommand(p, "§2[Valider]", "/run");
				return true;
			
			
			
			} else if(cmd.getName().equalsIgnoreCase("run") && sender.hasPermission("run.use") && EnumState.getState().equals(EnumState.WAIT)) {
			
				JSON.sendAll("§2La partie commence !", "§6Bonne chance !", 5);
				
				for(Player player : Bukkit.getOnlinePlayers())
					if(!player.getGameMode().equals(GameMode.SPECTATOR)) {
						PlayerInGame.playerInGame.add(player.getUniqueId());
						player.setHealth(20);
						player.setFoodLevel(20);
						player.getInventory().clear();
						player.setGameMode(GameMode.SURVIVAL);
						
						player.setStatistic(Statistic.PLAYER_KILLS, 0);
						
						ScoreBoard.send(player);
					}
				
				for (Entity e : Main.world.getEntities())
					if(e instanceof Monster)
						e.remove();
				
				Main.timer.run();
				EnumState.setState(EnumState.RUNNING);
				return true;
			}
		}
		return false;
	}

	
	
	
	
	
}
