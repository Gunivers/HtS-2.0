package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.Options.BorderOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.AddBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.BorderScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.KilledScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.PlayerScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.RemoveBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.TimerScoreboardOption;
import fr.HtSTeam.HtS.Options.Structure.GUIManager;

public class OptionsRegister {
	
	public static GUIManager main = new GUIManager("Options", 3, "Options", "Ouvre les options", Material.BARRIER, null);
	public static GUIManager uhc = new GUIManager("UHC", 1, "UHC", "Régler les UHC", Material.GOLDEN_APPLE, OptionsRegister.main);
	public static GUIManager scoreboard = new GUIManager("Scoreboard", 1, "Scoreboard", "Régler le scoreboard", Material.SIGN, OptionsRegister.main);
	
	public static void register() {
		new BorderOption();
		
		// Scoreboard
		new PlayerScoreboardOption();
		new KilledScoreboardOption();
		new TimerScoreboardOption();
		new BorderScoreboardOption();		
		new AddBlankScoreboardOption();
		new RemoveBlankScoreboardOption();
	}
	
}
