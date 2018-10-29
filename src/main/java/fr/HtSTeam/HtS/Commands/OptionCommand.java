package fr.HtSTeam.HtS.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Player.Player;

public class OptionCommand implements CommandExecutor {


	public OptionCommand() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = Player.instance((org.bukkit.entity.Player) sender);
			if (cmd.getName().equalsIgnoreCase("option") && sender.hasPermission("option.use")) {
				GUIRegister.main.open(p);
				return true;
			
			
			
			} if (cmd.getName().equalsIgnoreCase("test") && sender.hasPermission("test.use")) {
//				for(UUID p2 : PlayerInGame.playerInGame)
//					System.out.println(Bukkit.getPlayer(p2).getDisplayName());
				return true;
			}
		}
		
		return false;
	}
}
