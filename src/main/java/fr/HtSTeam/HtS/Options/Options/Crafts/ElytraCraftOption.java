package fr.HtSTeam.HtS.Options.Options.Crafts;

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

public class ElytraCraftOption extends OptionBuilder implements Alterable {

	private boolean activate = true;
	
	public ElytraCraftOption() {
		super(Material.ELYTRA, "Craft des Elytra", "§2Activé", "Activé", OptionRegister.crafts);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		setState(activate);
	}

	@Override
	public void setState(boolean value) {
		activate = value;
		if(value) {
			try {
				FileExtractor.extractFile(FileExtractor.cr + "elytra.json", FileExtractor.wdir + FileExtractor.Rdir);
				setValue("Activé");
				getItemStack().setLore("§2Activé");
				
			} catch (IOException | URISyntaxException e) {
				activate = false;
				e.printStackTrace();
			}
			
		} else {
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Rdir + "elytra.json"));
				setValue("Désactivé");
				getItemStack().setLore("§4Désactivé");
				
			} catch (IOException e) {
				activate = true;
				e.printStackTrace();
			}
		}
		
		parent.update(this);
	}
}
