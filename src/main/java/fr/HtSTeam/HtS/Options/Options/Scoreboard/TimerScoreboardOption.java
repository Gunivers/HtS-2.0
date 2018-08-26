package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class TimerScoreboardOption extends OptionBuilder<Null> {
	
	public TimerScoreboardOption() {
		super(Material.WATCH, "Timer", "Afficher le temps", null, GUIRegister.scoreboard);
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
		
		if (!ScoreBoard.scoreboards.containsKey(p.getUniqueId())) {
			ScoreBoard.send(p);
		} else {
			ScoreBoard.scoreboards.get(p.getUniqueId()).deactivate();
			ScoreBoard.scoreboards.remove(p.getUniqueId());
			ScoreBoard.send(p);
		}
	}
	
	@Override
	public void setState(Null value) {}

	@Override
	public String description() {return null;}
}
