package fr.HtSTeam.HtS.Options.Options.Presets;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.Tag;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;
import fr.HtSTeam.HtS.Utils.Files.XmlFile;

public class LoadPreset extends OptionBuilder<Null> implements CommandExecutor {

	File file;
	
	public LoadPreset() {
		super(Material.ENDER_CHEST, "Charger", "Charge une configuration préalablement sauvegardée", null, GUIRegister.presets, false);
	}

	@Override
	public void event(Player p) {
		p.closeInventory();
		file = new File(Main.plugin.getDataFolder() + "/" + "Presets");
		p.sendMessage("§a===================");
		for(File f : file.listFiles((dir, name) -> name.endsWith(".xml"))) {
			LinkedHashMap<String, String> line = new LinkedHashMap<String, String>();
			line.put(f.getName().replaceAll(".xml", "") + "  ", null);
			line.put("§6[Charger]", "/load " + f.getName().replaceAll(".xml", ""));
//			JSON.sendJsonRunCommand(p, line);
		}
		p.sendMessage("§a===================");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player && cmd.getName().equalsIgnoreCase("load") && sender.hasPermission("load.use")) {
			if(args.length == 1) {
				for(File f : file.listFiles((dir, name) -> name.endsWith(".xml"))) {
					if(f.getName().replaceAll(".xml", "").equals(args[0])) {
						load(f, (Player)sender);
						return true;
					}
				}
				sender.sendMessage("§4Fichier introuvable.");
				return true;
			}
			sender.sendMessage("§4Le nombre d'argument requis est de 1.");
			return true;
		}
		return false;
	}
	
	public void load(File f, Player p) {
		XmlFile xml = new XmlFile("Presets", f.getName().replaceAll(".xml", ""));
		ArrayList<Tag> contents = xml.get();
		loop:
		for(Tag content : contents) {
			for(OptionIO oio : OptionIO.optionIOClass) {
				if(content.attributes.get("name").equals(oio.getId())) {
					if(content.values.size() == 1)
						oio.load(content.values.get(0).name);
					else
						oio.load(content);
					continue loop;
				}
			}
		}
		for(Player p2 : Bukkit.getOnlinePlayers())
			if(p2.isOp()) 
				p2.sendMessage("§2Import de " + f.getName().replaceAll(".xml", "") + " réussi.");
	}
		
	@Override
	public void setState(Null value) {}
	
	@Override
	public String description() {
		return null;
	}
}
