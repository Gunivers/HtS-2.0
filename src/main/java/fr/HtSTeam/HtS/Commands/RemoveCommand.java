package fr.HtSTeam.HtS.Commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Players.PlayerInGame;

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
					for (UUID uuid2: PlayerInGame.playerInGame) {
						if (PlayerInGame.uuidToName.get(uuid2).equals(args[0])) {
							uuid = uuid2;
							name = args[0];
							break;
						}
					}
					if (uuid != null) {
						final UUID final_uuid = uuid;
						final String final_name = name;		
						new Reflections("fr.HtSTeam.HtS").getSubTypesOf(Listener.class).forEach(clazz -> { for (Method m : clazz.getMethods()) try { m.invoke(null, final_uuid, final_name); } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { e.printStackTrace(); } });
					} else
						p.sendMessage("§4Ce joueur n'existe pas !");
					return true;
				}
			}
		}
		return false;
	}
}
