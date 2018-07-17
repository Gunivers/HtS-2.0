package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class PlayerScoreboardOption extends OptionBuilder<Null> {

	public PlayerScoreboardOption() {
		super(Material.TOTEM, "Joueurs", "Afficher le nombre de joueurs encore en vie (et le nombre de teams)", null, GUIRegister.scoreboard);
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
		
		if (!ScoreBoard.scoreboards.containsKey(p.getUniqueId())) {
			ScoreBoard.send(p);
		} else {
			ScoreBoard.scoreboards.get(p.getUniqueId()).deactivate();
			ScoreBoard.scoreboards.remove(p.getUniqueId());
			ScoreBoard.send(p);
		}
	}
}
