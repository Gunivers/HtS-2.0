package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.World;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Player.Player;

public class DifficultyOption extends OptionBuilder<Difficulty> implements StartTrigger
{
	public int index = Arrays.asList(Difficulty.values()).indexOf(Difficulty.HARD);
	
	public DifficultyOption()
	{
		super(Material.IRON_BARS, "Difficulté", "§4Difficile", Difficulty.HARD, GUIRegister.other, true);
	}

	@Override
	public void event(Player p)
	{
		this.index = index + 1 % 4;
		this.setState(Difficulty.values()[index]);
	}

	@Override
	public void setState(Difficulty value)
	{
		String lore = "§";
		
		switch (value)
		{
			case EASY:
				this.getItemStack().setType(Material.ZOMBIE_HEAD);
				lore += "6";
				break;
				
			case HARD:
				this.getItemStack().setType(Material.WITHER_SKELETON_SKULL);
				lore += "4";
				break;
				
			case NORMAL:
				this.getItemStack().setType(Material.SKELETON_SKULL);
				lore += "5";
				break;
				
			case PEACEFUL:
				this.getItemStack().setType(Material.PLAYER_HEAD);
				lore += "2";
				break;
		}
		
		lore += value.toString();
		this.getItemStack().setLore(lore);
		
		this.setValue(value);
		parent.update(this);
	}

	@Override
	public String description()
	{
		return "§2Difficulté: §6" + Difficulty.values()[index].toString();
	}

	@Override
	public void onPartyStart()
	{
		for (World world : Bukkit.getWorlds())
			world.setDifficulty(Difficulty.values()[index]);
	}
}
