package fr.HtSTeam.HtS.Commands;

import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Utils.JSON;

public class StartCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main main;
	
	public StartCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			System.out.println(EnumState.getState().equals(EnumState.WAIT));
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("start") && sender.hasPermission("start.use") && EnumState.getState().equals(EnumState.WAIT)) {
				for(Entry<OptionsManager, Object> entry : OptionsManager.optionsList.entrySet()) {
					OptionsManager key = entry.getKey();
					Object value = entry.getValue();
					if(value != null && key.getDefaultValue() != null  && !key.getDefaultValue().equals(value))
						p.sendMessage(key.getName() + " : §4" + value.toString());
				}
				JSON.sendJsonRunCommand(p, "§2[Valider]", "/run");
				return true;
			} else if(cmd.getName().equalsIgnoreCase("run") && sender.hasPermission("run.use") && EnumState.getState().equals(EnumState.WAIT)) {
				EnumState.setState(EnumState.RUNNING);
				JSON.sendAll("§2La partie commence !", "§6Bonne chance !", 5);
				return true;
			}
		}
		return false;
	}

}
