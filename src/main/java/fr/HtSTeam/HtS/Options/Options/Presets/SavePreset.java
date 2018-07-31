package fr.HtSTeam.HtS.Options.Options.Presets;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.OptionIO;
import fr.HtSTeam.HtS.Utils.Tag;
import fr.HtSTeam.HtS.Utils.XmlFile;

public class SavePreset extends OptionBuilder<Null> {

	private int request = 0; 
	private Player p;
	private File file;
	
	public SavePreset() {
		super(Material.FEATHER, "Sauvegarder", "Sauvegarde la configuration actuelle", null, GUIRegister.presets, false);
	}

	@Override
	public void event(Player p) {
		this.p = p;
		getParent().close(p);
		request = 1;
		p.sendMessage("§4Veuillez choisir le nom du fichier :");
	}
	
	@SuppressWarnings("serial")
	public void save(String name) {
		XmlFile f = new XmlFile("Presets", name);
		for(OptionIO ob : OptionIO.optionIOClass) {
			if(ob.save() != null) {
				HashMap<String, String> attr = new HashMap<String, String>() {{ put("name", ob.getId()); }};
				ArrayList<String> elements = ob.save();
				if(elements.size() == 1) {
					f.add(new Tag("option", attr, new ArrayList<Tag>(){{ add(new Tag(Objects.toString(elements.get(0)), null, null)); }}));
				} else {
					ArrayList<Tag> elmnts = new ArrayList<Tag>();
					for(String e : elements)
						elmnts.add(new Tag("element", null, new ArrayList<Tag>() {{ add(new Tag(e, null, null)); }}));			
					f.add(new Tag("option", attr, elmnts));
				}
			}
		}
		f.save();
		p.sendMessage("§2Preset enregistré.");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request == 1 && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			File file = new File(Main.plugin.getDataFolder() + "/" + "Presets" + "/", e.getMessage() + ".xml");
			if (file.exists()) {
				p.sendMessage("§4Un preset existe déjà sous ce nom, voulez-vous écraser ce fichier ? (o/n)");
				this.file = file;
				request = 2;
			} else {
				save(e.getMessage());
				request = 0;
				p = null;
			}
		} else if(request == 2 && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			if(e.getMessage().toLowerCase().charAt(0) == 'o') {
				file.delete();
				save(file.getName().replaceAll(".xml", ""));
				p = null;
				request = 0;
			} else if(e.getMessage().toLowerCase().charAt(0) == 'n') {
				p.sendMessage("§4Sauvegarde annulée.");
				p = null;
				request = 0;
			} else {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}

	@Override
	public void setState(Null value) {}

	@Override
	public String description() {
		return null;
	}

}
