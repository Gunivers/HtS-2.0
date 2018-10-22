package fr.HtSTeam.HtS.Commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.command.Command;

import fr.HtSTeam.HtS.Commands.Structure.CommandHandler;
import fr.HtSTeam.HtS.Commands.Structure.EnumCommand;
import fr.HtSTeam.HtS.Player.Player;

public class TestCommand {
	
	@CommandHandler
	public static boolean test(Player player, Command cmd, String label, String[] args) {
		player.sendMessage("Bonjour!");
		return true;
	}
	
	@SuppressWarnings("serial")
	@CommandHandler(EnumCommand.COMPLETE)
	public static HashMap<Integer, ArrayList<String>> test() {
		HashMap<Integer, ArrayList<String>> prop = new HashMap<Integer, ArrayList<String>>();
		prop.put(0, new ArrayList<String>() {{ add("foo"); add("bar"); add("baz"); }});
		prop.put(1, new ArrayList<String>() {{ add("qux"); add("quux"); add("quuz"); }});
		prop.put(2, new ArrayList<String>() {{ add("corge"); add("grault"); add("garply"); }});
		return prop;
	}
}
