package fr.HtSTeam.HtS.Commands;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Options.Structure.TeamManager;
import fr.HtSTeam.HtS.Utils.JSON;
import fr.HtSTeam.HtS.Utils.Randomizer;

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
				for(Entry<OptionsManager, Object> entry : OptionsManager.optionsList.entrySet()) {
					OptionsManager key = entry.getKey();
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
						Main.playerInGame.addPlayer(player);
						player.setHealth(20);
						player.setFoodLevel(20);
						player.getInventory().clear();
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 255, false, false));
						player.setGameMode(GameMode.SURVIVAL);
					}
				
				teleport();
				Main.timer.run();
				EnumState.setState(EnumState.RUNNING);
				return true;
			}
		}
		return false;
	}

	
	
	
	private void teleport() {
		
		int border = Integer.parseInt(OptionsRegister.borderOption.getValue());
		
		if(TeamManager.teamList.size() == 0) {
			for(Player player : Bukkit.getOnlinePlayers()) {
					int[] coords = Randomizer.RandCoord(-(border - 50) / 2, (border - 50) / 2, 255, 255, -(border - 50) / 2, (border - 50) / 2);
					player.teleport(new Location(player.getWorld(), coords[0], coords[1], coords[2]));
				}
		
		} else {
				for(TeamManager tm : TeamManager.teamList) {
					int[] coords = Randomizer.RandCoord(-(border - 50) / 2, (border - 50) / 2, 255, 255, -(border - 50) / 2, (border - 50) / 2);
					for(Entry<Player, TeamManager> entry : TeamManager.playerTeam.entrySet()) {
						if(entry.getValue() == tm)
							entry.getKey().teleport(new Location(entry.getKey().getWorld(), coords[0], coords[1], coords[2]));
					}
				}
		}
	}
	
}
