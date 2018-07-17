package fr.HtSTeam.HtS.Options.Options.Scoreboard;

import java.util.Collections;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Scoreboard.ScoreBoard;

public class RemoveBlankScoreboardOption extends OptionBuilder<Null> {
	
	public RemoveBlankScoreboardOption() {
		super(Material.EMPTY_MAP, "Supprimer Espace", "Supprimer une ligne vide", null, GUIRegister.scoreboard);
	}
		
	@Override
	public void event(Player p) {
			Collections.reverse(ScoreBoard.display);
			ScoreBoard.display.remove("AddBlankScoreboardOption");
			Collections.reverse(ScoreBoard.display);
			if (!ScoreBoard.scoreboards.containsKey(p.getUniqueId())) {
				ScoreBoard.send(p);
			} else {
				ScoreBoard.scoreboards.get(p.getUniqueId()).deactivate();
				ScoreBoard.scoreboards.remove(p.getUniqueId());
				ScoreBoard.send(p);
			}
	}
}
