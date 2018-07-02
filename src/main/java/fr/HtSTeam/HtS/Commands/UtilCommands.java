package fr.HtSTeam.HtS.Commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import fr.HtSTeam.HtS.Players.PlayerInGame;

public class UtilCommands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("heal") && sender.hasPermission("heal.use")) {
				if (args.length == 1) {
					for (UUID uuid : PlayerInGame.playerInGame)
						if (Bukkit.getPlayer(uuid).getName().equals(args[0])) {
							Bukkit.getPlayer(uuid).setHealth(20);
							return true;
						}
					sender.sendMessage("§4Le joueur n'existe pas !");

				}
			}
			if (cmd.getName().equalsIgnoreCase("feed") && sender.hasPermission("feed.use")) {
				if (args.length == 1) {
					for (UUID uuid : PlayerInGame.playerInGame)
						if (Bukkit.getPlayer(uuid).getName().equals(args[0])) {
							Bukkit.getPlayer(uuid).setFoodLevel(20);
							return true;
						}
					sender.sendMessage("§4Le joueur n'existe pas !");
				}
				return false;
			} if (cmd.getName().equalsIgnoreCase("test") && sender.hasPermission("test.use")) {
				 try {
					 ByteArrayOutputStream str = new ByteArrayOutputStream();	
						BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
						data.writeObject(((Player) sender).getInventory().getItemInMainHand());
						data.close();
						String s = Base64.getEncoder().encodeToString(str.toByteArray());
						System.out.println(s);
						ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(s));
				        BukkitObjectInputStream data2 = new BukkitObjectInputStream(stream);
				        System.out.println(((ItemStack)data2.readObject()).getItemMeta().getDisplayName());
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}

		}
		return false;
	}
}
