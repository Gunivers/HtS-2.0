package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Options.Structure.Annotation.PRIORITY;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;

public class AnnonceOption extends OptionsManager {

	public AnnonceOption() {
		super(Material.PAPER, "Annonce", "", "20", null);
		setValue("60");
	}

	@Override
	public void event(Player p) {}
	
	@Timer(PRIORITY.HIGHEST)
	public void firstAnnonce() {
		Bukkit.broadcastMessage("§4Les paysans voisins commencent à devenir dangereux, prenez-leur leur drapeaux afin de leur montrer qui reste le maître !");
		setValue("90");
	}
	
	@Timer(PRIORITY.LOWEST)
	public void secondAnnonce() {
		Bukkit.broadcastMessage("§4Maintenant, tout les coups sont permis, trouvez leur salle des coffres afin de les décribiliser auprès du Roi !");
	}

}
