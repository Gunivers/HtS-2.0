package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.Options.AtDeath.GoldenAppleOption;
import fr.HtSTeam.HtS.Options.Options.AtDeath.HeadOption;
import fr.HtSTeam.HtS.Options.Options.Base.BorderOption;
import fr.HtSTeam.HtS.Options.Options.Base.BreathOption;
import fr.HtSTeam.HtS.Options.Options.Base.DayLightCycleOption;
import fr.HtSTeam.HtS.Options.Options.Base.DifficultOption;
import fr.HtSTeam.HtS.Options.Options.Base.EnablePvPOption;
import fr.HtSTeam.HtS.Options.Options.Base.FixDayOption;
import fr.HtSTeam.HtS.Options.Options.Base.NoRegenOption;
import fr.HtSTeam.HtS.Options.Options.Base.WeatherOption;
import fr.HtSTeam.HtS.Options.Options.GameMode.FallenKingdomOption;
import fr.HtSTeam.HtS.Options.Options.GameMode.GameModeGUI;
import fr.HtSTeam.HtS.Options.Options.GameMode.UHCOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.BatOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.ChestOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.CrateOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.FishingOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.GhastOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.SkeletonOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.ZombieOption;
import fr.HtSTeam.HtS.Options.Options.Mobs.CreeperNerfOption;
import fr.HtSTeam.HtS.Options.Options.Mobs.SkeletonNerfOption;
import fr.HtSTeam.HtS.Options.Options.Modifier.AlgueUrticanteOption;
import fr.HtSTeam.HtS.Options.Options.Modifier.HeadShot;
import fr.HtSTeam.HtS.Options.Options.Modifier.ModifiersGUI;
import fr.HtSTeam.HtS.Options.Options.Modifier.ShulkerShellOption;
import fr.HtSTeam.HtS.Options.Options.Nether.NetherWartOption;
import fr.HtSTeam.HtS.Options.Options.Nether.ShulkerNetherOption;
import fr.HtSTeam.HtS.Options.Options.Others.NuggetsOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.AddBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.BorderScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.KilledScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.PlayerScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.RemoveBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.TimerScoreboardOption;
import fr.HtSTeam.HtS.Options.Structure.GUIManager;

public class OptionsRegister {
	
	public final static GUIManager main = new GUIManager("Options", 3, "Options", "Ouvre les options", Material.BARRIER, null);
	public static GUIManager gameMode = new GameModeGUI();
	public static GUIManager base = new GUIManager("Option de base", 1, "Option de base", "Régler les options basiques", Material.GRASS, OptionsRegister.main);
	public static GUIManager atDeath = new GUIManager("Mort du joueur", 1, "Mort du joueur", "Régler la mort d'un joueur", Material.SKULL_ITEM, OptionsRegister.main);
	public static GUIManager scoreboard = new GUIManager("Scoreboard", 1, "Scoreboard", "Régler le scoreboard", Material.SIGN, OptionsRegister.main);
	public static GUIManager mob = new GUIManager("Mobs", 1, "Mobs", "Désactiver des mobs", Material.SKULL_ITEM, OptionsRegister.main);
	public static GUIManager modifiers = new ModifiersGUI();
	public static GUIManager loottables = new GUIManager("Loot Tables", 1, "Loot Tables", "Régler les Loot Tables", Material.BOOK, OptionsRegister.main);
	public static GUIManager nether = new GUIManager("Nether", 1, "Nether", "Régler le Nether", Material.NETHERRACK, OptionsRegister.main);
	public static GUIManager other = new GUIManager("Autre", 1, "Autre", "Options inclassables", Material.CHEST, OptionsRegister.main);
	

	
	public static BorderOption borderOption;
	
	public static void register() {
		
		//GameMode
		new UHCOption();
		new FallenKingdomOption();
		
		// Mobs
		new CreeperNerfOption();
		new SkeletonNerfOption();
		
		// Modifiers
		new ShulkerShellOption();
		new AlgueUrticanteOption();
		new HeadShot();
		
		// atPlayer
		atDeath.getItemStackManager().setItem(Material.SKULL_ITEM, (short) 3);
		main.update(atDeath);
		new GoldenAppleOption();
		new HeadOption();
		
		// Base
		borderOption = new BorderOption();
		new DifficultOption();
		new NoRegenOption();
		new DayLightCycleOption();
		new FixDayOption();
		new WeatherOption();
		new EnablePvPOption();
		new BreathOption();
		
		// Scoreboard
		new PlayerScoreboardOption();
		new KilledScoreboardOption();
		new TimerScoreboardOption();
		new BorderScoreboardOption();		
		new AddBlankScoreboardOption();
		new RemoveBlankScoreboardOption();
		
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
		new ShulkerNetherOption();
		
		//Other
		new NuggetsOption();
	}
	
}
