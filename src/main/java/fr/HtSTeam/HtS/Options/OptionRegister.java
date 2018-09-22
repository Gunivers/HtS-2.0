package fr.HtSTeam.HtS.Options;

import fr.HtSTeam.HtS.Options.Options.AtDeath.GoldenAppleOption;
import fr.HtSTeam.HtS.Options.Options.AtDeath.HeadOption;

import fr.HtSTeam.HtS.Options.Options.Base.BorderOption;
import fr.HtSTeam.HtS.Options.Options.Base.BreathOption;
import fr.HtSTeam.HtS.Options.Options.Base.DayLightCycleOption;
import fr.HtSTeam.HtS.Options.Options.Base.DifficultOption;
import fr.HtSTeam.HtS.Options.Options.Base.EnablePvPOption;
import fr.HtSTeam.HtS.Options.Options.Base.FixDayOption;
import fr.HtSTeam.HtS.Options.Options.Base.NoDamageOption;
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
import fr.HtSTeam.HtS.Options.Options.Others.HeadShotOption;
import fr.HtSTeam.HtS.Options.Options.Others.NuggetsOption;

import fr.HtSTeam.HtS.Options.Options.Presets.LoadPreset;
import fr.HtSTeam.HtS.Options.Options.Presets.SavePreset;
import fr.HtSTeam.HtS.Options.Options.Presets.Disablers.DisabledCraftsPreset;
import fr.HtSTeam.HtS.Options.Options.Presets.Disablers.DisabledEnchantsPreset;
import fr.HtSTeam.HtS.Options.Options.Presets.Disablers.DisabledPotionsPreset;

import fr.HtSTeam.HtS.Options.Options.Probability.AppleDropProbaOption;
import fr.HtSTeam.HtS.Options.Options.Probability.FlintDropProbaOption;
import fr.HtSTeam.HtS.Options.Options.Probability.SpiderJockeyProbaOption;
import fr.HtSTeam.HtS.Options.Options.Probability.ZombieJockeyProbaOption;

import fr.HtSTeam.HtS.Options.Options.Statistics.ArrowHitStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.ArrowShotStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.DamageGivenStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.DamageReceivedStatOption;

import fr.HtSTeam.HtS.Options.Options.Statistics.DisconnectionStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.EnchantmentsDoneStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.GoldenAppleEatenStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.ItemsPickedUpStatOption;

import fr.HtSTeam.HtS.Options.Options.Statistics.KillsMonsterStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.KillsPassiveStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.KillsPlayerNameStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.KillsPlayerStatOption;

import fr.HtSTeam.HtS.Options.Options.Statistics.MinedDiamondsStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.MinedGoldOresStatOption;

import fr.HtSTeam.HtS.Options.Options.Statistics.PortalsCrossedStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.PotionDrunkStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.PotionThrownStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.TargetsStatOption;

import fr.HtSTeam.HtS.Options.Options.Statistics.TimePlayedStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.TimeSneakedStatOption;
import fr.HtSTeam.HtS.Options.Options.Statistics.TimeSprintedStatOption;

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
		public static NoDamageOption noDamage = new NoDamageOption();
		
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
		
		//Probability
		public static AppleDropProbaOption appleDropProbability = new AppleDropProbaOption();
		public static FlintDropProbaOption flintDropProbability = new FlintDropProbaOption();
		public static SpiderJockeyProbaOption SpiderJockeyProbability = new SpiderJockeyProbaOption();
		public static ZombieJockeyProbaOption ZombieJockeyProbability = new ZombieJockeyProbaOption();
		
		
		//Other
		public static NuggetsOption nuggetBucket = new NuggetsOption();
		public static AlgueUrticanteOption alguae = new AlgueUrticanteOption();
		public static HeadShotOption headShot = new HeadShotOption();
		
		public static BorderOption borderOption;
		
		//Presets
		public static SavePreset savePreset = new SavePreset();
		public static LoadPreset loadPreset = new LoadPreset();
		
		public static DisabledCraftsPreset disabledCraftsPreset = new DisabledCraftsPreset();
		public static DisabledEnchantsPreset disabledEnchantsPreset = new DisabledEnchantsPreset();
		public static DisabledPotionsPreset disabledPotionsPreset = new DisabledPotionsPreset();
		
		// Statistics
		
		public static TimePlayedStatOption stats1 = new TimePlayedStatOption();
		public static TimeSprintedStatOption stats2 = new TimeSprintedStatOption();
		public static TimeSneakedStatOption stats3 = new TimeSneakedStatOption();
		public static DisconnectionStatOption stats4 = new DisconnectionStatOption();
		public static PortalsCrossedStatOption stats5 = new PortalsCrossedStatOption();
		public static MinedDiamondsStatOption stats6 = new MinedDiamondsStatOption();
		public static MinedGoldOresStatOption stats7 = new MinedGoldOresStatOption();
		public static ItemsPickedUpStatOption stats8 = new ItemsPickedUpStatOption();
		public static EnchantmentsDoneStatOption stats9 = new EnchantmentsDoneStatOption();
		public static GoldenAppleEatenStatOption stats10 = new GoldenAppleEatenStatOption();
		public static PotionDrunkStatOption stats11 = new PotionDrunkStatOption();
		public static PotionThrownStatOption stats12 = new PotionThrownStatOption();
		public static KillsPlayerStatOption stats13 = new KillsPlayerStatOption();
		public static KillsMonsterStatOption stats14 = new KillsMonsterStatOption();
		public static KillsPassiveStatOption stats15 = new KillsPassiveStatOption();
		public static DamageGivenStatOption stats16 = new DamageGivenStatOption();
		public static DamageReceivedStatOption stats17 = new DamageReceivedStatOption();
		public static ArrowShotStatOption stats18 = new ArrowShotStatOption();
		public static ArrowHitStatOption stats19 = new ArrowHitStatOption();
		public static TargetsStatOption stats20 = new TargetsStatOption();
		public static KillsPlayerNameStatOption stats21 = new KillsPlayerNameStatOption();
		
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
		}
}
