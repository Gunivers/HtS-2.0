package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class AddBlankScoreboardOption extends OptionsManager {
	
	public AddBlankScoreboardOption() {
		super(Material.PAPER, "Espace", "Ajoute une ligne vide", null, OptionsRegister.scoreboard);
	}
		
	@Override
	public void event(Player p) {
			ScoreBoard.display.add("AddBlankScoreboardOption");
			if (!ScoreBoard.scoreboards.containsKey(p)) {
				ScoreBoard.send(p);
			} else {
				ScoreBoard.scoreboards.get(p).deactivate();
				ScoreBoard.scoreboards.remove(p);
				ScoreBoard.send(p);
			}
	}
}