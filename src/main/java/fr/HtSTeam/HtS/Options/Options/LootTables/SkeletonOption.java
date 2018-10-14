package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.entity.EntityType;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Files.FileExtractor;

public class SkeletonOption extends OptionBuilder<Boolean> {
	
	
	public SkeletonOption() {
		super(new ItemStackBuilder(EntityType.SKELETON, 1, "§rSquelette", "§4Désactivé"), false, GUIRegister.loottables, false);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if(value && !getValue()) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "skeleton.json", FileExtractor.wdir + FileExtractor.Edir);
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(!value && getValue()){
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Edir + "skeleton.json"));
				getItemStack().setLore("§4Désactivé");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setValue(value);
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}

}