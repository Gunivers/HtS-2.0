package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DifficultOption extends OptionBuilder<Difficulty> {
	
	private int difficult = 3;
	
	public DifficultOption() {
		super(Material.SKELETON_SKULL, "Difficulté", "§4Difficile", Difficulty.HARD, GUIRegister.base);
		changeDifficult(Difficulty.HARD);
	}

	@Override
	public void event(Player p) {
		difficult = (difficult + 1) % 4;
		switch(difficult) {
		case 0 : setState(Difficulty.EASY);
				 break;
		case 1 : setState(Difficulty.NORMAL);
				break;
		case 2 : setState(Difficulty.HARD);
				break;
		case 3: setState(Difficulty.PEACEFUL);
				break;
		}
	}
	
	private void setLore(Difficulty d) {
		switch(d) {
		case PEACEFUL: getItemStack().setLore("§2Paisible");
		 			   break;
		case EASY : getItemStack().setLore("§eFacile");
			     break;
		case NORMAL : getItemStack().setLore("§6Normal");
				 break;
		case HARD : getItemStack().setLore("§4Difficile");
				break;
		}
		parent.update(this);
	}
	
	private void changeDifficult(Difficulty d) {
		for(World world : Bukkit.getWorlds())
			world.setDifficulty(d);
	}

	@Override
	public void setState(Difficulty value) {
		setLore(value);
		changeDifficult(value);
		setValue(value);
		
	}

	@Override
	public String description() {
		return "§2[Aide]§r La difficulté est en " + getItemStack().getLore().substring(2).toLowerCase() + ".";
	}
	
	@Override
	public void load(Object o) {
		try {
			setValue(Difficulty.valueOf(o.toString()));
		} catch(IllegalArgumentException e) {
			return;
		}
	}

}
