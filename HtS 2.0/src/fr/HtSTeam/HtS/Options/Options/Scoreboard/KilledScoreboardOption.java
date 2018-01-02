package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class KilledScoreboardOption extends OptionsManager {
	
	public KilledScoreboardOption() {
		super(Material.SKULL_ITEM, "Kill", "Afficher le nombre de joueurs tués", null, OptionsRegister.scoreboard);
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
		
		if (!ScoreBoard.scoreboards.containsKey(p.getUniqueId())) {
			ScoreBoard.send(p);
		} else {
			ScoreBoard.scoreboards.get(p.getUniqueId()).deactivate();
			ScoreBoard.scoreboards.remove(p.getUniqueId());
			ScoreBoard.send(p);
		}
	}
}
