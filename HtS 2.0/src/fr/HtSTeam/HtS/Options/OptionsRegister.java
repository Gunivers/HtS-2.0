package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.Options.Scoreboard.AddBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.BorderScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.KilledScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.PlayerScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.RemoveBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.TimerScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Team.FriendlyFireOption;
import fr.HtSTeam.HtS.Options.Options.UHC.BorderOption;
import fr.HtSTeam.HtS.Options.Options.UHC.DayLightCycleOption;
import fr.HtSTeam.HtS.Options.Options.UHC.DifficultOption;
import fr.HtSTeam.HtS.Options.Options.UHC.FixDayOption;
import fr.HtSTeam.HtS.Options.Options.UHC.NoRegenOption;
import fr.HtSTeam.HtS.Options.Options.UHC.WeatherOption;
import fr.HtSTeam.HtS.Options.Structure.GUIManager;

public class OptionsRegister {
	
	public static GUIManager main = new GUIManager("Options", 3, "Options", "Ouvre les options", Material.BARRIER, null);
	public static GUIManager uhc = new GUIManager("UHC", 1, "UHC", "Régler les UHC", Material.GOLDEN_APPLE, OptionsRegister.main);
	public static GUIManager scoreboard = new GUIManager("Scoreboard", 1, "Scoreboard", "Régler le scoreboard", Material.SIGN, OptionsRegister.main);
	public static GUIManager teams = new GUIManager("Equipes", 1, "Equipes", "Régler les équipes", Material.BANNER, OptionsRegister.main);
	public static GUIManager mob = new GUIManager("Mobs", 1, "Mobs", "Activer/Désactiver des mobs", Material.SKULL_ITEM, OptionsRegister.main);
	
	public static BorderOption borderOption;
	
	public static void register() {
		borderOption = new BorderOption();
		new DifficultOption();
		new NoRegenOption();
		new DayLightCycleOption();
		new FixDayOption();
		new WeatherOption();
		
		// Scoreboard
		new PlayerScoreboardOption();
		new KilledScoreboardOption();
		new TimerScoreboardOption();
		new BorderScoreboardOption();		
		new AddBlankScoreboardOption();
		new RemoveBlankScoreboardOption();
		
		// Team
		new FriendlyFireOption();
	}
	
}
