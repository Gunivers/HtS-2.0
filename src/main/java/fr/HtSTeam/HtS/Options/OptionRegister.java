package fr.HtSTeam.HtS.Options;

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
import fr.HtSTeam.HtS.Options.Options.Crafts.ElytraCraftOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.BatOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.ChestOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.CrateOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.FishingOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.GhastOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.SkeletonOption;
import fr.HtSTeam.HtS.Options.Options.LootTables.ZombieOption;
import fr.HtSTeam.HtS.Options.Options.Mobs.CreeperNerfOption;
import fr.HtSTeam.HtS.Options.Options.Mobs.MobBuddyOption;
import fr.HtSTeam.HtS.Options.Options.Mobs.SkeletonNerfOption;
import fr.HtSTeam.HtS.Options.Options.Nether.NetherWartOption;
import fr.HtSTeam.HtS.Options.Options.Nether.ShulkerNetherOption;
import fr.HtSTeam.HtS.Options.Options.Others.AlgueUrticanteOption;
import fr.HtSTeam.HtS.Options.Options.Others.HeadShot;
import fr.HtSTeam.HtS.Options.Options.Others.NuggetsOption;
import fr.HtSTeam.HtS.Options.Options.Presets.LoadPreset;
import fr.HtSTeam.HtS.Options.Options.Presets.SavePreset;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.AddBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.BorderScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.KilledScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.PlayerScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.RemoveBlankScoreboardOption;
import fr.HtSTeam.HtS.Options.Options.Scoreboard.TimerScoreboardOption;

public class OptionRegister {
	
		//Mobs
		public static CreeperNerfOption creeperNerf = new CreeperNerfOption();
		
		//Modifiers
		//public static Alterable shulkerShell = new ShulkerShellOption();
		
		//AtDeath
		public static GoldenAppleOption goldenApple = new GoldenAppleOption();
		public static HeadOption head = new HeadOption();
		
		//Base
		public static NoRegenOption noRegen =  new NoRegenOption();
		public static WeatherOption weather = new WeatherOption();
		
		// Loot Tables
		public static BatOption batLoot = new BatOption();
		public static GhastOption ghastLoot = new GhastOption();
		public static SkeletonOption skeletonLoot = new SkeletonOption();
		public static ZombieOption zombieLoot = new ZombieOption();
		public static ChestOption chestContent = new ChestOption();
		public static CrateOption crateContent = new CrateOption();
		public static FishingOption fishingLoot = new FishingOption();
		
		// Nether
		public static NetherWartOption netherWart = new NetherWartOption();
		public static ShulkerNetherOption shulkerNether = new ShulkerNetherOption();
		
		//Crafts
		public static ElytraCraftOption elytraCraft = new ElytraCraftOption();
		
		//Other
		public static NuggetsOption nuggetBucket = new NuggetsOption();
		public static AlgueUrticanteOption alguae = new AlgueUrticanteOption();
		public static HeadShot headShot = new HeadShot();
		
		public static BorderOption borderOption;
		public static SavePreset savePreset = new SavePreset();
		public static LoadPreset loadPreset = new LoadPreset();
		
		// Statistics
		
		
		
		public static void register() {
			
			
			
			// Mobs
			new SkeletonNerfOption();
			new MobBuddyOption();			
			
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
