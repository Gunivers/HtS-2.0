package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.FileExtractor;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public class ZombieOption extends OptionBuilder<Boolean> implements Alterable {
	
	
	public ZombieOption() {
		super(new ItemStackBuilder(EntityType.ZOMBIE, 1, "§rZombie", "§4Désactivé"), false, OptionRegister.loottables);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(boolean value) {
		if(value && !getValue()) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "zombie.json", FileExtractor.wdir + FileExtractor.Edir);
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(!value && getValue()) {
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Edir + "zombie.json"));
				getItemStack().setLore("§4Désactivé");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setValue(value);
		parent.update(this);
	}
}