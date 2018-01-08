package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DifficultOption extends OptionBuilder {
	
	private int difficult = 3;
	
	public DifficultOption() {
		super(Material.SKULL_ITEM, "Difficulté", "§4Difficile", "Difficile", OptionRegister.base);
		changeDifficult(Difficulty.HARD);
	}

	@Override
	public void event(Player p) {
		difficult = (difficult + 1) % 4;
		switch(difficult) {
			case 0 : changeDifficult(Difficulty.PEACEFUL);
					 setValue("Paisible");
					 getItemStackManager().setLore("§2Paisible");
					 break;
			case 1 : changeDifficult(Difficulty.EASY);
					 setValue("Facile");
					 getItemStackManager().setLore("§eFacile");
					 break;
			case 2 : changeDifficult(Difficulty.NORMAL);
					 setValue("Normal");
					 getItemStackManager().setLore("§6Normal");
				 	 break;
			case 3 : changeDifficult(Difficulty.HARD);
					 setValue("Difficile");
					 getItemStackManager().setLore("§4Difficile");
					 break;
		}
		parent.update(this);
		
	}
	
	private void changeDifficult(Difficulty d) {
		for(World world : Bukkit.getWorlds())
			world.setDifficulty(d);
	}

}
