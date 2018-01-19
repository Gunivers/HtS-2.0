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
import fr.HtSTeam.HtS.Options.Options.GameMode.SyTOption;
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
import fr.HtSTeam.HtS.Options.Options.Modifier.ModifiersGUI;
import fr.HtSTeam.HtS.Options.Options.Modifier.ShulkerShellOption;
import fr.HtSTeam.HtS.Options.Options.Nether.NetherWartOption;
import fr.HtSTeam.HtS.Options.Options.Nether.ShulkerNetherOption;
import fr.HtSTeam.HtS.Options.Options.Others.AlgueUrticanteOption;
import fr.HtSTeam.HtS.Options.Options.Others.HeadShot;
import fr.HtSTeam.HtS.Options.Options.Others.NuggetsOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.AddBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.BorderScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.KilledScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.PlayerScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.RemoveBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.TimerScoreboardOption;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;

public class OptionRegister {
	
	public final static GUIBuilder main = new GUIBuilder("Options", 3, "Options", "Ouvre les options", Material.BARRIER, null);
	public static GUIBuilder gameMode = new GameModeGUI();
	public static GUIBuilder base = new GUIBuilder("Option de base", 1, "Option de base", "Régler les options basiques", Material.GRASS, OptionRegister.main);
	public static GUIBuilder atDeath = new GUIBuilder("Mort du joueur", 1, "Mort du joueur", "Régler la mort d'un joueur", Material.SKULL_ITEM, OptionRegister.main);
	public static GUIBuilder scoreboard = new GUIBuilder("Scoreboard", 1, "Scoreboard", "Régler le scoreboard", Material.SIGN, OptionRegister.main);
	public static GUIBuilder mob = new GUIBuilder("Mobs", 1, "Mobs", "Désactiver des mobs", Material.SKULL_ITEM, OptionRegister.main);
	public static GUIBuilder modifiers = new ModifiersGUI();
	public static GUIBuilder loottables = new GUIBuilder("Loot Tables", 1, "Loot Tables", "Régler les Loot Tables", Material.BOOK, OptionRegister.main);
	public static GUIBuilder nether = new GUIBuilder("Nether", 1, "Nether", "Régler le Nether", Material.NETHERRACK, OptionRegister.main);
	public static GUIBuilder other = new GUIBuilder("Autre", 1, "Autre", "Options inclassables", Material.CHEST, OptionRegister.main);
	
	
	// GameMode
	public static GUIBuilder uhc = new UHCOption();
	public static GUIBuilder fallenKingdom = new FallenKingdomOption();
	public static GUIBuilder syt = new SyTOption();

	//Mobs
	public static Alterable creeperNerf = new CreeperNerfOption();
	
	//Modifiers
	public static Alterable shulkerShell = new ShulkerShellOption();
	
	
	//Other
	public static Alterable alguae = new AlgueUrticanteOption();
	public static Alterable headShot = new HeadShot();
	
	//AtDeath
	public static Alterable goldenApple = new GoldenAppleOption();
	public static Alterable head = new HeadOption();
	
	//Base
	public static Alterable noRegen =  new NoRegenOption();
	public static Alterable weather = new WeatherOption();
	
	// Loot Tables
	public static Alterable batLoot = new BatOption();
	public static Alterable ghastLoot = new GhastOption();
	public static Alterable skeletonLoot = new SkeletonOption();
	public static Alterable zombieLoot = new ZombieOption();
	public static Alterable chestContent = new ChestOption();
	public static Alterable crateContent = new CrateOption();
	public static Alterable fishingLoot = new FishingOption();
	
	// Nether
	public static Alterable netherWart = new NetherWartOption();
	public static Alterable shulkerNether = new ShulkerNetherOption();
	
	//Other
	public static Alterable nuggetBucket = new NuggetsOption();
	
	
	public static BorderOption borderOption;
	
	public static void register() {
		
		
		
		// Mobs
		new SkeletonNerfOption();
				
		
		// AtDeath
		atDeath.getItemStack().setItem(Material.SKULL_ITEM, (short) 3);
		main.update(atDeath);
		
		
		// Base
		borderOption = new BorderOption();
		new DifficultOption();
		new DayLightCycleOption();
		new FixDayOption();
		new EnablePvPOption();
		new BreathOption();
		
		// Scoreboard
		new PlayerScoreboardOption();
		new KilledScoreboardOption();
		new TimerScoreboardOption();
		new BorderScoreboardOption();		
		new AddBlankScoreboardOption();
		new RemoveBlankScoreboardOption();
	}
	
}
