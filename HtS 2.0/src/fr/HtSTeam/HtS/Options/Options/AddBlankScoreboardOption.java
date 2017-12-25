package fr.HtSTeam.HtS.Options.Options;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.ScoreBoard;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class AddBlankScoreboardOption extends OptionsManager {
	
	public AddBlankScoreboardOption() {
		super(Material.PAPER, "Ajout Esapce", "Ajoute une ligne vide", false, OptionsRegister.scoreboard);
	}
		
	@Override
	public void event(Player p) {
			ScoreBoard.display.add("AddBlankScoreboardOption");

	}
}
