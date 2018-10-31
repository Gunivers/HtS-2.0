package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import fr.HtSTeam.HtS.Player.Player;

public class PlayerSyT extends Player {
	
	private PlayerSyT target;
	private PlayerSyT hunter;
	
	
//	METHOS --------------------------------------------------------------------------------------
	
	
	// RETURN
	
	
	/**
	 * Returns the target of this player
	 * @return PlayerSyT
	 */
	public PlayerSyT getTarget() {
		return target;
	}
	
	
	/**
	 * Returns the hunter of this player
	 * @return PlayerSyT
	 */
	public PlayerSyT getHunter() {
		return hunter;
	}
	
	
	// NO RETURN
	
	
	/**
	 * Sets the target of this player
	 * @param target
	 */
	public void setTarget(PlayerSyT target) {
		target.setHuntert(hunter);
		this.target = target;
	}
	
	
	/**
	 * Sets the hunter of this player
	 * @param hunter
	 */
	private void setHuntert(PlayerSyT hunter) {
		this.hunter = hunter;
	}
}