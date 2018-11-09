package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.entity.EntityType;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Files.FileExtractor;

public class BatOption extends Option<Boolean> {
		
	public BatOption() {
		super(new ItemStackBuilder(EntityType.BAT, 1, "§rChauve-souris", "§4Désactivé"), false, GUIRegister.loottables);
	}

	@Override
	public void event(Player p) {
		setState(!value);
	}

	@Override
	public void setState(Boolean value) {
		if(value && !this.value) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "bat.json", FileExtractor.wdir + FileExtractor.Edir);
				this.value = true;
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(!value && this.value){
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Edir + "bat.json"));
				this.value = false;
				getItemStack().setLore("§4Désactivé");
			} catch (IOException e) {
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