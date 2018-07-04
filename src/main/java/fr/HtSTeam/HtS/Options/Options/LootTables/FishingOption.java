package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.FileExtractor;

public class FishingOption extends OptionBuilder<Boolean> implements Alterable {
		
	public FishingOption() {
		super(Material.FISHING_ROD, "Pêche modifiée", "§4Désactivé", false, OptionRegister.loottables);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(boolean value) {
		if(value && !getValue()) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "fishing.json", FileExtractor.wdir + FileExtractor.Gdir);
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(!value && getValue()){
			getItemStack().setLore("§4Désactivé");
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Gdir + "fishing.json"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setValue(value);
		parent.update(this);
	}
}