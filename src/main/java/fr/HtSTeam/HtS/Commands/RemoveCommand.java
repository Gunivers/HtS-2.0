package fr.HtSTeam.HtS.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.Structure.Annotation.RemovePlayer;
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
						if (Bukkit.getOfflinePlayer(uuid2).getName().equals(args[0]) || Bukkit.getPlayer(uuid2).getName().equals(args[0])) {
							uuid = uuid2;
							name = args[0];
							break;
						}
					}
					if (uuid != null) {
						final UUID final_uuid = uuid;
						final String final_name = name;		
						new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(RemovePlayer.class).forEach(m -> { try { m.invoke(null, final_uuid, final_name); } catch (IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException e) { e.printStackTrace();	} });
					} else
						p.sendMessage("§4Ce joueur n'existe pas !");
					return true;
				}
			}
		}
		return false;
	}
}
