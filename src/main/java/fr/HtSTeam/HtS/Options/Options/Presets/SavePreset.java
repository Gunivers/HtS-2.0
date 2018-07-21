package fr.HtSTeam.HtS.Options.Options.Presets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.OptionIO;
import fr.HtSTeam.HtS.Utils.XmlFile;

public class SavePreset extends OptionBuilder<Null> {

	public SavePreset() {
		super(Material.FEATHER, "Sauvegarder", "Sauvegarde la configuration actuelle", null, GUIRegister.presets);
	}

	@Override
	public void event(Player p) {
		getParent().close(p);
		XmlFile f = new XmlFile("Presets", "test");
		f.root("preset", null, null);
		for(OptionIO ob : OptionIO.optionIOClass) {
			if(ob.save() != null) {
				HashMap<String, String> attr = new HashMap<String, String>();
				attr.put("name", ob.getId());
				ArrayList<String> elements = ob.save();
				if(elements.size() == 1) {
					f.set("option", attr, Objects.toString(elements.get(0)), "preset", null, null);
				} else {
					f.set("option", attr, null, "preset", null, null);
					for(String s : elements)
						f.sub("element", null, s);
				}
			}
		}
		f.save();
		p.sendMessage("§2Preset enregistré.");
	}

	@Override
	public void setState(Null value) {}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

}
