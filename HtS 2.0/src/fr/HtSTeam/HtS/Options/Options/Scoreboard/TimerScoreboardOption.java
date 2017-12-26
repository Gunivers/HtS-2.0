package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.ScoreBoard;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class TimerScoreboardOption extends OptionsManager {
	
	public TimerScoreboardOption() {
		super(Material.WATCH, "Ajout du timer", "Ajoute la durée écoulée puis le début", null, OptionsRegister.scoreboard);
	}
	
	private boolean activated = false;
	
	@Override
	public void event(Player p) {
		if(activated) {
			activated = false;
			ScoreBoard.display.remove("TimerScoreboardOption");
		} else {
			activated = true;
			ScoreBoard.display.add("TimerScoreboardOption");
		}
	}
	
}
