package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.Options.BorderOption;

public class OptionsRegister {
	
	public static GUIManager main = new GUIManager("Options", 3, "Options", "Ouvre les options", Material.BARRIER, null);
	public static GUIManager option1 = new GUIManager("Test", 1, "Test", "Ouvre les options 2", Material.SLIME_BALL, OptionsRegister.main);
	public static GUIManager option2 = new GUIManager("Test 2", 1, "Test 2", "Ouvre les options 3", Material.DIAMOND, OptionsRegister.main);

	public static void register() {
		new BorderOption();
	}
	
}
