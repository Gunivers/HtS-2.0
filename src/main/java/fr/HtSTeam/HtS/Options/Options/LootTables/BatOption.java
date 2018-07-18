package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.FileExtractor;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public class BatOption extends OptionBuilder<Boolean> {
		
	public BatOption() {
		super(new ItemStackBuilder(EntityType.BAT, 1, "§rChauve-souris", "§4Désactivé"), false, GUIRegister.loottables, false);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if(value && !getValue()) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "bat.json", FileExtractor.wdir + FileExtractor.Edir);
				setValue(true);
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(!value && getValue()){
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Edir + "bat.json"));
				setValue(false);
				getItemStack().setLore("§4Désactivé");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		parent.update(this);		
	}

	@Override
	public String description() {
		return null;
	}
}