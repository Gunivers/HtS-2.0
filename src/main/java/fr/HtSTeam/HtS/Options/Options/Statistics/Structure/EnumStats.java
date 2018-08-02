package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

public enum EnumStats {

	TIME_PLAYED (true, 0),
	DISCONNECTIONS (true, 0),
	TIME_SPRINTED (true, 0),
	TIME_SNEAKED (true, 0),
	PORTALS_CROSSED (true, 0),
	MINED_DIAMONDS (true, 0),
	MINED_GOLDORES (true, 0),
	ITEMS_PICKED_UP (true, 0),
	ENCHANTMENTS_DONE (true, 0),
	GOLDEN_APLLE_EATEN (true, 0),
	POTION_DRUNK (true, 0),
	POTION_THROWN (true, 0),
	KILLS_PLAYER (true, 0),
	KILLS_MONSTER (true, 0),
	KILLS_PASSIVE (true, 0),
	DAMAGE_GIVEN (true, 0),
	DAMAGE_RECEIVED (true, 0),
	ARROW_SHOT (true, 0),
	ARROW_HIT (true, 0),
	ACCURACY (true, 0);
	
	private boolean b;
	private Object o;
	
	private EnumStats(Boolean b, Object o) { 
		this.b = b;
		this.o = o;
	}
	
	public void setTracked(boolean b) { this.b = b; }
	public boolean isTracked() { return b; }
	
	public Object getDefaultValue() { return o; }
}