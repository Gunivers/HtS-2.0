package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.Options.GameMode.FallenKingdomOption;
import fr.HtSTeam.HtS.Options.Options.GameMode.GameModeGUI;
import fr.HtSTeam.HtS.Options.Options.GameMode.SyTOption;
import fr.HtSTeam.HtS.Options.Options.GameMode.UHCOption;
import fr.HtSTeam.HtS.Options.Options.Modifier.ModifiersGUI;
import fr.HtSTeam.HtS.Options.Options.StartingStuff.StartingStuffGUI;
import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;

public class GUIRegister {

	public final static GUIBuilder main = new GUIBuilder("Options", 3, "Options", "Ouvre les options", Material.BARRIER, null);
	
	
	public static GUIBuilder gameMode = new GameModeGUI();
	// GameMode
	public static GUIBuilder hts = new UHCOption();
	public static GUIBuilder fallenKingdom = new FallenKingdomOption();
	public static GUIBuilder syt = new SyTOption();
	
	public static GUIBuilder base = new GUIBuilder("Option de base", 1, "Option de base", "Régler les options basiques", Material.GRASS, main);
	public static GUIBuilder atDeath = new GUIBuilder("Mort du joueur", 1, "Mort du joueur", "Régler la mort d'un joueur", Material.SKULL_ITEM,main);
	public static GUIBuilder scoreboard = new GUIBuilder("Scoreboard", 1, "Scoreboard", "Régler le scoreboard", Material.SIGN, main);
	public static GUIBuilder mob = new GUIBuilder("Mobs", 1, "Mobs", "Désactiver des mobs", Material.SKULL_ITEM, main);
	public static GUIBuilder modifiers = new ModifiersGUI();
	public static GUIBuilder loottables = new GUIBuilder("Loot Tables", 1, "Loot Tables", "Régler les Loot Tables", Material.BOOK, main);
	public static GUIBuilder nether = new GUIBuilder("Nether", 1, "Nether", "Régler le Nether", Material.NETHERRACK, main);
	public static GUIBuilder crafts = new GUIBuilder("Crafts", 1, "Crafts","Gérer les crafts customs", Material.WORKBENCH, main);
	public static GUIBuilder other = new GUIBuilder("Autre", 1, "Autre", "Options inclassables", Material.CHEST, main);
	public static GUIBuilder disableMob = new GUIBuilder("Désactivation Mobs", 5, "Désactivation Mobs", "Désactiver le spawn de certains mobs", Material.MONSTER_EGGS, mob);
	public static StartingStuffGUI startingStuff = new StartingStuffGUI();

	public static GUIBuilder presets = new GUIBuilder("Presets", 1, "Presets", "Permet de définir une configuration enregistrée", Material.RECORD_5, main);
	
	public static GUIBuilder stats = new GUIBuilder("Statistiques", 3, "Statistiques", "Permet de définir les statistiques trackées", Material.BOOK_AND_QUILL, main);
	
	
}
