package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSyT implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			if(cmd.getName().equalsIgnoreCase("radar") && sender.hasPermission("radar.use")) {
				if(args.length == 1) {
//					if(args[0].equals("1"))
//						new ActionBar("§4§lCible repérée : X Y Z", 30).sendAll();
//					else if(args[0].equals("2"))
//						new ActionBar("§4§lTrop faible signal détecté : impossible de localiser la cible.", 30).sendAll();
//					else if(args[0].equals("3"))
//						new ActionBar("§4§lAucun signal détecté : impossible de localiser la cible.", 30).sendAll();
				}
				return true;
			
			} else if(cmd.getName().equalsIgnoreCase("target") && SyT.targetCycleOption.targetCycle.contains(((Player) sender).getUniqueId())) {
				sender.sendMessage("Votre cible est : §l§c" + PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget((Player) sender)));
				
				
				return true;
			}
		}
		return false;
	}
}
