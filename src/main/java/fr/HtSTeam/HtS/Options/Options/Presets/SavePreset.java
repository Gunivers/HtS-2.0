package fr.HtSTeam.HtS.Options.Options.Presets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
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
		for(Entry<OptionBuilder<?>, Object> ob : OptionBuilder.optionsList.entrySet()) {
			if(ob.getKey().getValue() != null && !ob.getKey().getValue().equals(ob.getKey().getDefaultValue())) { // Erreur dans la condition
				HashMap<String, String> attr = new HashMap<String, String>();
				attr.put("name", ob.getKey().getName());
				if(!(ob.getKey().getValue() instanceof ArrayList<?>)) {
					f.set("option", attr, Objects.toString(ob.getKey().getValue()), "preset", null, null);
				} else {
					f.set("option", attr, null, "preset", null, null);
					ArrayList<?> list = (ArrayList<?>) ob.getKey().getValue();
					for(Object o : list)
						f.sub("element", null, o.toString());
				}
			}
		}
		f.save();
		p.sendMessage("§2Fichier enregistré dans " + Main.plugin.getDataFolder());
	}

	@Override
	public void setState(Null value) {}

}
