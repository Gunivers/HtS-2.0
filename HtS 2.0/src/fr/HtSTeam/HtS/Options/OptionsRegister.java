package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.Options.AtDeath.GoldenAppleOption;
import fr.HtSTeam.HtS.Options.Options.AtDeath.HeadOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.BatOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.ChestOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.CrateOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.FishingOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.GhastOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.SkeletonOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.ZombieOption;
import fr.HtSTeam.HtS.Options.Options.Mobs.CreeperNerfOption;
import fr.HtSTeam.HtS.Options.Options.Modifier.AlgueUrticanteOption;
import fr.HtSTeam.HtS.Options.Options.Modifier.HeadShot;
import fr.HtSTeam.HtS.Options.Options.Modifier.ModifiersGUI;
import fr.HtSTeam.HtS.Options.Options.Modifier.ShulkerShellOption;
import fr.HtSTeam.HtS.Options.Options.Nether.NetherWartOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.AddBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.BorderScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.KilledScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.PlayerScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.RemoveBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.TimerScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Team.CollisionTeamOption;
import fr.HtSTeam.HtS.Options.Options.Team.DeathMeassageTeamOption;
import fr.HtSTeam.HtS.Options.Options.Team.FriendlyFireOption;
import fr.HtSTeam.HtS.Options.Options.Team.NameTagTeamOption;
import fr.HtSTeam.HtS.Options.Options.Team.SeeInvisibleOption;
import fr.HtSTeam.HtS.Options.Options.UHC.BorderOption;
import fr.HtSTeam.HtS.Options.Options.UHC.BreathOption;
import fr.HtSTeam.HtS.Options.Options.UHC.DayLightCycleOption;
import fr.HtSTeam.HtS.Options.Options.UHC.DifficultOption;
import fr.HtSTeam.HtS.Options.Options.UHC.FixDayOption;
import fr.HtSTeam.HtS.Options.Options.UHC.NoRegenOption;
import fr.HtSTeam.HtS.Options.Options.UHC.WeatherOption;
import fr.HtSTeam.HtS.Options.Structure.GUIManager;

public class OptionsRegister {
	
	public final static GUIManager main = new GUIManager("Options", 3, "Options", "Ouvre les options", Material.BARRIER, null);
	public static GUIManager uhc = new GUIManager("UHC", 1, "UHC", "Régler les UHC", Material.GOLDEN_APPLE, OptionsRegister.main);
	public static GUIManager atDeath = new GUIManager("Mort du joueur", 1, "Mort du joueur", "Options de la mort d'un joueur", Material.SKULL_ITEM, OptionsRegister.main);
	public static GUIManager scoreboard = new GUIManager("Scoreboard", 1, "Scoreboard", "Régler le scoreboard", Material.SIGN, OptionsRegister.main);
	public static GUIManager teams = new GUIManager("Equipes", 1, "Equipes", "Régler les équipes", Material.BANNER, OptionsRegister.main);
	public static GUIManager mob = new GUIManager("Mobs", 1, "Mobs", "Activer/Désactiver des mobs", Material.SKULL_ITEM, OptionsRegister.main);
	public static GUIManager modifiers = new ModifiersGUI();
	public static GUIManager loottables = new GUIManager("loottables", 1, "loottables", "Activer/Désactiver des loottables", Material.BOOK, OptionsRegister.main);
	public static GUIManager nether = new GUIManager("nether", 1, "nether", "Activer/Désactiver des nether", Material.NETHERRACK, OptionsRegister.main);

	
	public static BorderOption borderOption;
	
	public static void register() {
		
		// Mobs
		new CreeperNerfOption();
		
		// Modifiers
		new ShulkerShellOption();
		new AlgueUrticanteOption();
		new HeadShot();
		
		// atPlayer
		atDeath.getItemStackManager().setItem(Material.SKULL_ITEM, (short) 3);
		main.update(atDeath);
		new GoldenAppleOption();
		new HeadOption();
		
		// UHC
		borderOption = new BorderOption();
		new DifficultOption();
		new NoRegenOption();
		new DayLightCycleOption();
		new FixDayOption();
		new WeatherOption();
		new BreathOption();
		
		// Scoreboard
		new PlayerScoreboardOption();
		new KilledScoreboardOption();
		new TimerScoreboardOption();
		new BorderScoreboardOption();		
		new AddBlankScoreboardOption();
		new RemoveBlankScoreboardOption();
		
		// Team
		new FriendlyFireOption();
		new SeeInvisibleOption();
		new NameTagTeamOption();
		new CollisionTeamOption();
		new DeathMeassageTeamOption();
		
		// Loot Tables
		new BatOption();
		new GhastOption();
		new SkeletonOption();
		new ZombieOption();
		new ChestOption();
		new CrateOption();
		new FishingOption();
		
		// Nether
		new NetherWartOption();
	}
	
}
