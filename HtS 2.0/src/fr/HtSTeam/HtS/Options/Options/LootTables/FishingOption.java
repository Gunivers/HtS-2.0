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

public class FishingOption extends OptionBuilder implements Alterable {
	
	private boolean activate = false;
	
	public FishingOption() {
		super(Material.FISHING_ROD, "Pêche modifiée", "§4Désactivé", "Désactivé", OptionRegister.loottables);
	}

	@Override
	public void event(Player p) {
		activate =! activate;
		setState(activate);
	}

	@Override
	public void setState(boolean value) {
		activate = value;
		if(value) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "fishing.json", FileExtractor.wdir + FileExtractor.Gdir);
				setValue("Activé");
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			setValue("Désactivé");
			getItemStack().setLore("§4Désactivé");
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Gdir + "fishing.json"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		parent.update(this);
	}
}