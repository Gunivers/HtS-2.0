package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.PRIORITY;

public class AnnonceOption extends OptionBuilder<Integer> {

	public AnnonceOption() {
		super(Material.PAPER, "Annonce", "", 60, null);
	}

	@Override
	public void event(Player p) {}
	
	@Timer(PRIORITY.HIGHEST)
	public void firstAnnonce() {
		Bukkit.broadcastMessage("§4Les paysans voisins commencent à devenir dangereux, prenez-leur leur drapeaux afin de leur montrer qui reste le maître !");
		setValue(80);
	}
	
	@Timer(PRIORITY.LOWEST)
	public void secondAnnonce() {
		Bukkit.broadcastMessage("§4Maintenant, tout les coups sont permis, trouvez leur salle des coffres afin de les décribiliser auprès du Roi !");
	}

	@Override
	public void setState(Integer value) {}

	@Override
	public String description() {
		return "CTF";
		
	}

}
