package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import org.bukkit.command.Command;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Commands.Structure.CommandHandler;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Player.PlayerSyT;
import fr.HtSTeam.HtS.Utils.Lang;

public class CommandSyT {

	@CommandHandler
	public static boolean radar(Player sender, Command cmd, String msg, String[] args) {
		if (!sender.hasPermission("radar.use") || args.length != 1)
			return false;
		
		if(args[0].equals("1"))
			Player.forEach(player -> player.sendActionBar(Lang.get("radar.command.1"), 30, false));
		else if(args[0].equals("2"))
			Player.forEach(player -> player.sendActionBar(Lang.get("radar.command.2"), 30, false));
		else if(args[0].equals("3"))
			Player.forEach(player -> player.sendActionBar(Lang.get("radar.command.3"), 30, false));
		return true;
	}
	
	@CommandHandler
	public static boolean target(Player sender, Command cmd, String msg, String[] args) {
		if (!Main.gamemode.gamemodeToString().equalsIgnoreCase("SyT"))
			return false;
		
		sender.sendMessage(Lang.get("target.command").replaceAll(Lang.REPLACE, ((PlayerSyT) sender).getTarget().getDisplayName()), false);
		return true;
	}
}