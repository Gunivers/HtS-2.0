package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class AddBlankScoreboardOption extends OptionBuilder<Null> {
	
	public AddBlankScoreboardOption() {
		super(Material.PAPER, "Espace", "Ajouter une ligne vide", null, OptionRegister.scoreboard);
	}
		
	@Override
	public void event(Player p) {
			ScoreBoard.display.add("AddBlankScoreboardOption");
			if (!ScoreBoard.scoreboards.containsKey(p.getUniqueId())) {
				ScoreBoard.send(p);
			} else {
				ScoreBoard.scoreboards.get(p.getUniqueId()).deactivate();
				ScoreBoard.scoreboards.remove(p.getUniqueId());
				ScoreBoard.send(p);
			}
	}
}