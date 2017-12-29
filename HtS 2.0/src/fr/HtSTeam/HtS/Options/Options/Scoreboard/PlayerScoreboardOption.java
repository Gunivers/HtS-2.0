package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.ScoreBoard;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class PlayerScoreboardOption extends OptionsManager {

	public PlayerScoreboardOption() {
		super(Material.TOTEM, "Joueurs", "Affiche le nombre de joueurs encore en vie (et le nombre de teams)", null, OptionsRegister.scoreboard);
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
	}
}
