package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.FileExtractor;

public class CrateOption extends OptionBuilder<Boolean>{
		
	public CrateOption() {
		super(Material.BROWN_SHULKER_BOX, "Crate", "§4Désactivé", false, GUIRegister.loottables);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if(value && !getValue()) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "crate.json", FileExtractor.wdir + FileExtractor.Cdir);
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(!value && getValue()){
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Cdir + "crate.json"));
				getItemStack().setLore("§4Désactivé");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setValue(value);
		parent.update(this);
	}
}