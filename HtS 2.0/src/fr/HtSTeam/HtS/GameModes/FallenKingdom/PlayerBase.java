package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum PlayerBase {
	NONE("§4MESSAGE ENTER NO MANS LAND"),
	OWN("§4MESSAGE ENTER OUR BASE"),
	OTHER("§4MESSAGE ENTER ENEMY BASE");
	
	private String msg;

	PlayerBase(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() { return msg; }
	
	public static PlayerBase isInBase(Player p, Location l) {
		for (BaseManager b : BaseManager.baseList) {
			int pos[][] = b.getOrderPos();
			if (pos[0][0] < l.getBlockX() && l.getBlockX() < pos[1][0] && pos[0][1] < l.getBlockZ() && l.getBlockZ() < pos[1][1]) {
				if(BaseManager.playerBase.get(p.getUniqueId()) == b)
					return PlayerBase.OWN;
				return PlayerBase.OTHER;
			}
		}
		return PlayerBase.NONE;
	}
}
