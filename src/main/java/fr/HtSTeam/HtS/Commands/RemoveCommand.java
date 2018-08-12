package fr.HtSTeam.HtS.Commands;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;

public class RemoveCommand implements CommandExecutor {

	Main main;

	public RemoveCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("remove") && sender.hasPermission("remove.use")) {
				if (args.length == 1) {
					UUID uuid = null;
					String name = null;
					for (Entry<Player, UUID> players : main.uuidPlayer.entrySet()) {
						if (players.getKey().getName().equals(args[0])) {
							uuid = players.getValue();
							name = args[0];
							break;
						}
					}
					if (uuid != null) {
						// TODO
					} else
						p.sendMessage("§4Ce joueur n'existe pas !");
					return true;
				}
			}
		}
		return false;
	}
}
