package fr.HtSTeam.HtS.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionsRegister;

public class OptionCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main main;

	public OptionCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("option") && sender.hasPermission("option.use")) {
				OptionsRegister.main.open(p);
				return true;
			}
			
		}
		
		return false;
	}
}
