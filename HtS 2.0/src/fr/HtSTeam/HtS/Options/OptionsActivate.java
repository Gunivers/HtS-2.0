package fr.HtSTeam.HtS.Options;

import org.bukkit.Material;

public class OptionsActivate extends OptionsManager {

	public OptionsActivate(String name, String description, Material material, boolean b) {
		super(name, description, material);
		OptionsManager.optionsList.put(this, b);
	}

}
