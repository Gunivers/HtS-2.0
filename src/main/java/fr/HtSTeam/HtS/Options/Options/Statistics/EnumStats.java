package fr.HtSTeam.HtS.Options.Options.Statistics;

import fr.HtSTeam.HtS.EnumState;

public enum EnumStats {

	TIME_PLAYED (true),
	DISCONNECTIONS (true),
	TIME_SPRINTED (true),
	TIME_SNEAKED (true),
	PORTALS_CROSSED (true),
	MINED_DIAMONDS (true),
	MINED_GOLDORES (true),
	ITEMS_PICKED_UP (true),
	ENCHANTMENTS_DONE (true),
	GOLDEN_APLLE_EATEN (true),
	POTION_DRUNK (true),
	POTION_THROWN (true),
	KILLS_PLAYER (true),
	KILLS_MONSTER (true),
	KILLS_PASSIVE (true),
	DAMAGE_GIVEN (true),
	DAMAGE_RECEIVED (true),
	ARROW_SHOT (true),
	ARROW_HIT (true),
	ACCURACY (true);
	
	private boolean b = false;
	
	private EnumStats(Boolean b) { 
		if (EnumState.getState().equals(EnumState.RUNNING) && b)
			return;
		this.b = b;
		StatisticHandler.updateTrackedStats();
	}
	
	public void setTracked(boolean b) { this.b = b; }
	public boolean isTracked() { return b; }
}