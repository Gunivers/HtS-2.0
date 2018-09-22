package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

import org.bukkit.Difficulty;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;

/**
 * 
 * @author A~Z
 *
 */
public class WorldDifficultyOption extends OptionBuilder<Difficulty> implements StartTrigger
{
	public final Difficulty[] diff = Difficulty.values();
	public int index = Arrays.asList(diff).indexOf(Difficulty.HARD);
	
	public WorldDifficultyOption()
	{
		super(Material.IRON_BARS, "Difficulté", "Difficulté: §6HARD", Difficulty.HARD, GUIRegister.other, true);
	}

	@Override
	public void event(Player p)
	{
		this.index++;
		if (index >= diff.length) index = 0;
		
		this.setState(diff[index]);
		parent.update(this);
	}

	@Override
	public void setState(Difficulty value)
	{
		this.getItemStack().setLore("Difficulté: §6" + value.toString());
		this.setValue(value);
	}

	@Override
	public String description()
	{
		return "§2Difficulté: §6" + diff[index].toString();
	}

	@Override
	public void onPartyStart()
	{
		Main.world.setDifficulty(diff[index]);
	}
}
