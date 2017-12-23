package fr.HtSTeam.HtS.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;

public class Test implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main main;

	public Test(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {

			if (cmd.getName().equalsIgnoreCase("test") && sender.hasPermission("test.use")) {
				Bukkit.getServer().broadcastMessage("Test");
				return true;
			}
			
		}
		
		return false;
	}
}
