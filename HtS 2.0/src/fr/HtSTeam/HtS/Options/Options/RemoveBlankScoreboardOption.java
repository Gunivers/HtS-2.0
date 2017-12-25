package fr.HtSTeam.HtS.Options.Options;

import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.ScoreBoard;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class RemoveBlankScoreboardOption extends OptionsManager {
	
	public RemoveBlankScoreboardOption() {
		super(Material.EMPTY_MAP, "Supprime Esapce", "Supprime une ligne vide", false, OptionsRegister.scoreboard);
	}
		
	@Override
	public void event(Player p) {
			Collections.reverse(ScoreBoard.display);
			ScoreBoard.display.remove("AddBlankScoreboardOption");
			Collections.reverse(ScoreBoard.display);
	}
}
