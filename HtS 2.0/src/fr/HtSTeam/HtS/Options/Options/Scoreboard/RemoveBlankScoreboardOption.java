package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class RemoveBlankScoreboardOption extends OptionsManager {
	
	public RemoveBlankScoreboardOption() {
		super(Material.EMPTY_MAP, "Supprimer Espace", "Supprimer une ligne vide", null, OptionsRegister.scoreboard);
	}
		
	@Override
	public void event(Player p) {
			Collections.reverse(ScoreBoard.display);
			ScoreBoard.display.remove("AddBlankScoreboardOption");
			Collections.reverse(ScoreBoard.display);
			if (!ScoreBoard.scoreboards.containsKey(p)) {
				ScoreBoard.send(p);
			} else {
				ScoreBoard.scoreboards.get(p).deactivate();
				ScoreBoard.scoreboards.remove(p);
				ScoreBoard.send(p);
			}
	}
}
