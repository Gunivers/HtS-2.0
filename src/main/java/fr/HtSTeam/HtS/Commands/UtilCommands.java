package fr.HtSTeam.HtS.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;

public class UtilCommands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("heal") && sender.hasPermission("heal.use")) {
				if (args.length == 1) {
					for (UUID uuid : PlayerInGame.playerInGame)
						if (PlayerInGame.uuidToName.get(uuid).equals(args[0])) {
							Bukkit.getPlayer(uuid).setHealth(20);
							return true;
						}
					sender.sendMessage("§4Le joueur n'existe pas !");

				}
			}
			if (cmd.getName().equalsIgnoreCase("feed") && sender.hasPermission("feed.use")) {
				if (args.length == 1) {
					for (UUID uuid : PlayerInGame.playerInGame)
						if (PlayerInGame.uuidToName.get(uuid).equals(args[0])) {
							Bukkit.getPlayer(uuid).setFoodLevel(20);
							return true;
						}
					sender.sendMessage("§4Le joueur n'existe pas !");
				}
				return false;
			} if (cmd.getName().equalsIgnoreCase("test") && sender.hasPermission("test.use")) {
				/*for(int i = 0; i < OptionIO.optionIOClass.size(); i++)
					for(int j = 0; j < OptionIO.optionIOClass.size(); j++)
						if(i != j && OptionIO.optionIOClass.get(i).getId().equals(OptionIO.optionIOClass.get(j).getId()))
						System.out.println(OptionIO.optionIOClass.get(i));*/
				/*for(IconBuilder<?> es : IconBuilder.optionsList.keySet()) {
					if(es instanceof GoldenAppleOption)
						System.out.println(es);
				}*/
				for(OptionIO oio : OptionIO.optionIOClass)
					System.out.println(oio.getId());
			}

		}
		return false;
	}
}
