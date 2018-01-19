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

public class SkeletonOption extends OptionBuilder implements Alterable {
	
	private boolean activate = false;
	
	public SkeletonOption() {
		super(Material.MONSTER_EGG, "Squelette", "§4Désactivé", "Désactivé", OptionRegister.loottables);
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
				FileExtractor.extractFile(FileExtractor.lt + "skeleton.json", FileExtractor.wdir + FileExtractor.Edir);
				setValue("Activé");
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Edir + "skeleton.json"));
				setValue("Désactivé");
				getItemStack().setLore("§4Désactivé");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		parent.update(this);
	}
}