package fr.HtSTeam.HtS.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionsRegister;

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
				OptionsRegister.main.open((Player) sender);
				return true;
			}
			
		}
		
		return false;
	}
}
