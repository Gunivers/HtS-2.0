package fr.HtSTeam.HtS.Options.Options.Crafts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.Files.FileExtractor;

public class ElytraCraftOption extends Option<Boolean> {
	
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
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}
}
