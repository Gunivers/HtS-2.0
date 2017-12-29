package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class TimerScoreboardOption extends OptionsManager {
	
	public TimerScoreboardOption() {
		super(Material.WATCH, "Timer", "Affiche la durée écoulée puis le début de la partie", null, OptionsRegister.scoreboard);
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
		
		if (!ScoreBoard.scoreboards.containsKey(p)) {
			ScoreBoard.send(p);
		} else {
			ScoreBoard.scoreboards.get(p).deactivate();
			ScoreBoard.scoreboards.remove(p);
			ScoreBoard.send(p);
		}
	}
	
}
