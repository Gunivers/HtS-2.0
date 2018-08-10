package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.util.ArrayList;

public enum EnumStats {

	TIME_PLAYED (true, 0, "`TIME_PLAYED` INT NOT NULL"),
	TIME_SPRINTED (true, 0, "`TIME_SPRINTED` INT NOT NULL"),
	TIME_SNEAKED (true, 0, "`TIME_SNEAKED` INT NOT NULL"),
	DISCONNECTIONS (true, 0, "`DISCONNECTIONS` INT NOT NULL"),
	PORTALS_CROSSED (true, 0, "`PORTALS_CROSSED` INT NOT NULL"),
	MINED_DIAMONDS (true, 0, "`MINED_DIAMONDS` INT NOT NULL"),
	MINED_GOLDORES (true, 0, "`MINED_GOLDORES` INT NOT NULL"),
	ITEMS_PICKED_UP (true, 0, "`ITEMS_PICKED_UP` INT NOT NULL"),
	ENCHANTMENTS_DONE (true, 0, "`ENCHANTMENTS_DONE` INT NOT NULL"),
	GOLDEN_APPLE_EATEN (true, 0, "`GOLDEN_APPLE_EATEN` INT NOT NULL"),
	POTION_DRUNK (true, 0, "`POTION_DRUNK` INT NOT NULL"),
	POTION_THROWN (true, 0, "`POTION_THROWN` INT NOT NULL"),
	KILLS_PLAYER (true, 0, "`KILLS_PLAYER` INT NOT NULL"),
	KILLS_MONSTER (true, 0, "`KILLS_MONSTER` INT NOT NULL"),
	KILLS_PASSIVE (true, 0, "`KILLS_PASSIVE` INT NOT NULL"),
	DAMAGE_GIVEN (true, 0, "`DAMAGE_GIVEN` INT NOT NULL"),
	DAMAGE_RECEIVED (true, 0, "`DAMAGE_RECEIVED` INT NOT NULL"),
	ARROW_SHOT (true, 0, "`ARROW_SHOT` INT NOT NULL"),
	ARROW_HIT (true, 0, "`ARROW_HIT` INT NOT NULL");
	
	private boolean b;
	private Object o;
	private String dbCol;
	
	private EnumStats(Boolean b, Object o, String dbCol) { 
		this.b = b;
		this.o = o;
		this.dbCol = dbCol;
	}
	
	public void setTracked(boolean b) { this.b = b; }
	public boolean isTracked() { return b; }
	
	public static String getSQLTableStatsTracked() { 
		@SuppressWarnings("serial")
		ArrayList<String> cols = new ArrayList<String>() {{ add("`PLAYER_UUID` TEXT NOT NULL"); add("`PLAYER_NAME` TEXT NOT NULL"); }};
		for (int i = 0; i < values().length; i++)
			if (values()[i].isTracked())
				cols.add(values()[i].getSQLTableCol());
		if (!cols.isEmpty())
			return String.join(" , ", cols);
		return null; 
	}

	public static String getSQLInsertStatsTracked() { 
		@SuppressWarnings("serial")
		ArrayList<String> cols = new ArrayList<String>() {{ add("`PLAYER_UUID`"); add("`PLAYER_NAME`"); }};
		for (int i = 0; i < values().length; i++)
			if (values()[i].isTracked())
				cols.add("`" + values()[i].toString() + "`");
		if (!cols.isEmpty())
			return String.join(", ", cols);
		return null; 
	}
	
	public static ArrayList<String> getSQLUpdateStatsTracked() { 
		@SuppressWarnings("serial")
		ArrayList<String> cols = new ArrayList<String>() {{ add("PLAYER_NAME"); }};
		for (int i = 0; i < values().length; i++)
			if (values()[i].isTracked())
				cols.add(values()[i].toString());
		if (!cols.isEmpty())
			return cols;
		return null; 
	}
	
	public Object getDefaultValue() { return o; }
	
	public String getSQLTableCol() { return dbCol;}
}