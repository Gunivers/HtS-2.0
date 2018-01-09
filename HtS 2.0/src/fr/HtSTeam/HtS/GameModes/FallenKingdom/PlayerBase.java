package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum PlayerBase {
	NONE("§8Vous sortez de la base"),
	OWN("§2Vous entrez dans votre base"),
	OTHER("§4Vous entrez dans la base ennemie"),
	NEUTRAL("§fVous entrez dans un endroit neutre");
	
	private String msg;

	PlayerBase(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() { return msg; }
	
	public static PlayerBase isInBase(Player p, Location l) {
		for (BaseBuilder b : BaseBuilder.baseList) {
			int pos[][] = b.getOrderPos();
			if (pos[0][0] <= l.getBlockX() && l.getBlockX() <= pos[0][1] && pos[1][0] <= l.getBlockZ() && l.getBlockZ() <= pos[1][1]) {
				if(BaseBuilder.playerBase.get(p.getUniqueId()) == b)
					return PlayerBase.OWN;
				else if (b.isNeutral())
					return PlayerBase.NEUTRAL;
				return PlayerBase.OTHER;
			}
		}
		return PlayerBase.NONE;
	}
}
