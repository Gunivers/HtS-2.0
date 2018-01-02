package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class PlayerScoreboardOption extends OptionsManager {

	public PlayerScoreboardOption() {
		super(Material.TOTEM, "Joueurs", "Afficher le nombre de joueurs encore en vie (et le nombre de teams)", null, OptionsRegister.scoreboard);
	}
	
	private boolean activated = false;
	
	@Override
	public void event(Player p) {
		if(activated) {
			activated = false;
			ScoreBoard.display.remove("PlayerScoreboardOption");
		} else {
			activated = true;
			ScoreBoard.display.add("PlayerScoreboardOption");
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
