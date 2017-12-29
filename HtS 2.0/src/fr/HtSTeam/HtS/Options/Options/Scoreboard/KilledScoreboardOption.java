package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.ScoreBoard;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class KilledScoreboardOption extends OptionsManager {
	
	public KilledScoreboardOption() {
		super(Material.SKULL_ITEM, "Killed", "Affiche le nombre de joueurs killed", null, OptionsRegister.scoreboard);
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
