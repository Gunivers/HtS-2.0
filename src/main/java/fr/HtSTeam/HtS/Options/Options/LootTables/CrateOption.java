package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.Files.FileExtractor;

public class CrateOption extends Option<Boolean>{
		
	public CrateOption() {
		super(Material.BROWN_SHULKER_BOX, "Crate", "§4Désactivé", false, GUIRegister.loottables);
	}

	@Override
	public void event(Player p) {
		setState(!value);
	}

	@Override
	public void setState(Boolean value) {
		if(value && !this.value) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "crate.json", FileExtractor.wdir + FileExtractor.Cdir);
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(!value && this.value){
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Cdir + "crate.json"));
				getItemStack().setLore("§4Désactivé");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.value = value;
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}
}