package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.ScoreBoard;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class BorderScoreboardOption extends OptionsManager {
	
	public BorderScoreboardOption() {
		super(Material.IRON_FENCE, "Ajout Border", "Ajoute la taille de la border", null, OptionsRegister.scoreboard);
	}
	
	private boolean activated = false;
	
	@Override
	public void event(Player p) {
		if(activated) {
			activated = false;
			ScoreBoard.display.remove("BorderScoreboardOption");
		} else {
			activated = true;
			ScoreBoard.display.add("BorderScoreboardOption");
		}
	}
	
}