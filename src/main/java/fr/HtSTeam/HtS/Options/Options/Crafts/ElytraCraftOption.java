package fr.HtSTeam.HtS.Options.Options.Crafts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.FileExtractor;

public class ElytraCraftOption extends OptionBuilder<Boolean> {
	
	public ElytraCraftOption() {
		super(Material.ELYTRA, "Craft des Elytra", "§4Désactivé", false, GUIRegister.crafts);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if(value && !getValue()) {
				try {
					FileExtractor.extractFile(FileExtractor.cr + "elytra.json", FileExtractor.wdir + FileExtractor.Rdir);
					setValue(true);
					getItemStack().setLore("§2Activé");
					
				} catch (IOException | URISyntaxException e) {
					setValue(false);
					e.printStackTrace();
				}
		} else if(!value && getValue()) {
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Rdir + "elytra.json"));
				setValue(false);
				getItemStack().setLore("§4Désactivé");
				
			} catch (IOException e) {
				setValue(false);
				e.printStackTrace();
			}
		}
		parent.update(this);
	}
}
