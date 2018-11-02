package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.Options.GameMode.FallenKingdomOption;
import fr.HtSTeam.HtS.Options.Options.GameMode.GameModeGUI;
import fr.HtSTeam.HtS.Options.Options.GameMode.SyTOption;
import fr.HtSTeam.HtS.Options.Options.GameMode.UHCOption;
import fr.HtSTeam.HtS.Options.Options.Modifier.ModifiersGUI;
import fr.HtSTeam.HtS.Options.Options.StartingStuff.StartingStuffGUI;
import fr.HtSTeam.HtS.Options.Structure.Gui;

public class GUIRegister {

	public final static Gui main = new Gui("Options", 3, "Options", "Ouvre les options", Material.BARRIER, null);
	
	
	public static Gui gameMode = new GameModeGUI();
	// GameMode
	public static Gui hts = new UHCOption();
	public static Gui fallenKingdom = new FallenKingdomOption();
	public static Gui syt = new SyTOption();
	
	public static Gui base = new Gui("Option de base", 2, "Option de base", "Régler les options basiques", Material.GRASS, main);
	public static Gui atDeath = new Gui("Mort du joueur", 1, "Mort du joueur", "Régler la mort d'un joueur", Material.SKELETON_SKULL, main);
	public static Gui scoreboard = new Gui("Scoreboard", 1, "Scoreboard", "Régler le scoreboard", Material.SIGN, main);
	public static Gui mob = new Gui("Mobs", 1, "Mobs", "Désactiver des mobs", Material.ZOMBIE_HEAD, main);
	public static Gui modifiers = new ModifiersGUI();
	public static Gui loottables = new Gui("Loot Tables", 1, "Loot Tables", "Régler les Loot Tables", Material.BOOK, main);
	public static Gui nether = new Gui("Nether", 1, "Nether", "Régler le Nether", Material.NETHERRACK, main);
	public static Gui crafts = new Gui("Crafts", 1, "Crafts","Gérer les crafts customs", Material.CRAFTING_TABLE, main);
	public static Gui probability = new Gui("Probabilités", 1, "Probabilités", "Modifie les probabiltés", Material.FEATHER, main);
	public static Gui other = new Gui("Autre", 1, "Autre", "Options inclassables", Material.CHEST, main);
	public static Gui disableMob = new Gui("Désactivation Mobs", 5, "Désactivation Mobs", "Désactiver le spawn de certains mobs", Material.WOLF_SPAWN_EGG, mob);
	
	public static StartingStuffGUI startingStuff = new StartingStuffGUI();

	public static Gui stats = new Gui("Statistiques", 3, "Statistiques", "Permet de définir les statistiques trackées", Material.WRITABLE_BOOK, main);
	
	public static Gui presets = new Gui("Presets", 1, "Presets", "Permet de définir une configuration enregistrée", Material.MUSIC_DISC_WARD, main);
	
}
