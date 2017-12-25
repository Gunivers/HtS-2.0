package fr.HtSTeam.HtS.Options.Options;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.ScoreBoard;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class KilledScoreboardOption extends OptionsManager {
	
	public KilledScoreboardOption() {
		super(Material.SKULL_ITEM, "Nombres de joueurs tués", "Affiche le nombre de joueurs tués", false, OptionsRegister.scoreboard);
	}
	
	private boolean activated = false;
	
	@Override
	public void event(Player p) {
		if(activated) {
			activated = false;
			ScoreBoard.display.remove("KilledScoreboardOption");
		} else {
			activated = true;
			ScoreBoard.display.add("KilledScoreboardOption");
		}
	}
	
}
