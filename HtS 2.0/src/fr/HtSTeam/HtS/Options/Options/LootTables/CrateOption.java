package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Utils.FileExtractor;

public class CrateOption extends OptionsManager {
	
	private boolean activate = false;
	
	public CrateOption() {
		super(Material.BROWN_SHULKER_BOX, "Crate", "§4Désactivé", "Désactivé", OptionsRegister.loottables);
	}

	@Override
	public void event(Player p) {
		activate =! activate;
		if(activate) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "crate.json", FileExtractor.wdir + FileExtractor.Cdir);
				setValue("Activé");
				getItemStackManager().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Cdir + "crate.json"));
				setValue("Désactivé");
				getItemStackManager().setLore("§4Désactivé");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		parent.update(this);
	}
}