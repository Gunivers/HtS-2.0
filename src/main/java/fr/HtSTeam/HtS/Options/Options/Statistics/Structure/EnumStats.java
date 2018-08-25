package fr.HtSTeam.HtS.Options.Options.Statistics.Structure;

import java.util.ArrayList;
import java.util.HashSet;

public enum EnumStats {
	
	PLAYER_UUID (true, new HashSet<String>(), "`PLAYER_UUID` TEXT NOT NULL", null),
	PLAYER_NAME (true, new HashSet<String>(), "`PLAYER_NAME` TEXT NOT NULL", null),
	TIME_PLAYED (true, 0, "`TIME_PLAYED` INT NOT NULL", "Temps joué (s) :"),
	TIME_SPRINTED (true, 0, "`TIME_SPRINTED` INT NOT NULL", "Temps couru (s) :"),
	TIME_SNEAKED (true, 0, "`TIME_SNEAKED` INT NOT NULL", "Temps accroupi (s) :"),
	DISCONNECTIONS (true, 0, "`DISCONNECTIONS` INT NOT NULL", "Déconnexion :"),
	PORTALS_CROSSED (true, 0, "`PORTALS_CROSSED` INT NOT NULL", "Portails traversés :"),
	MINED_DIAMONDS (true, 0, "`MINED_DIAMONDS` INT NOT NULL", "Diamants minés :"),
	MINED_GOLDORES (true, 0, "`MINED_GOLDORES` INT NOT NULL", "Or minés :"),
	ITEMS_PICKED_UP (true, 0, "`ITEMS_PICKED_UP` INT NOT NULL", "Items ramassés :"),
	ENCHANTMENTS_DONE (true, 0, "`ENCHANTMENTS_DONE` INT NOT NULL", "Enchantements :"),
	GOLDEN_APPLE_EATEN (true, 0, "`GOLDEN_APPLE_EATEN` INT NOT NULL", "Pommes d'or mangées :"),
	POTION_DRUNK (true, 0, "`POTION_DRUNK` INT NOT NULL", "Potions bues :"),
	POTION_THROWN (true, 0, "`POTION_THROWN` INT NOT NULL", "Potions lancées :"),
	KILLS_PLAYER (true, 0, "`KILLS_PLAYER` INT NOT NULL", "Kills :"),
	KILLS_PLAYER_NAME (true, new HashSet<String>(), "`KILLS_PLAYER_NAME` TEXT NOT NULL", null),
	KILLS_MONSTER (true, 0, "`KILLS_MONSTER` INT NOT NULL", "Kills (monstres):"),
	KILLS_PASSIVE (true, 0, "`KILLS_PASSIVE` INT NOT NULL", "Kills (animaux):"),
	DAMAGE_GIVEN (true, 0, "`DAMAGE_GIVEN` INT NOT NULL", "Dégâts donnés :"),
	DAMAGE_RECEIVED (true, 0, "`DAMAGE_RECEIVED` INT NOT NULL", "Dégâts recus :"),
	ARROW_SHOT (true, 0, "`ARROW_SHOT` INT NOT NULL", "Fléches tirées :"),
	ARROW_HIT (true, 0, "`ARROW_HIT` INT NOT NULL", "Fléches touchées :"),
	TARGETS (true, new HashSet<String>(), "`TARGETS` TEXT NOT NULL", null);
	
	private boolean b;
	private Object o;
	private String dbCol;
	private String display_name;
	
	private EnumStats(Boolean b, Object o, String dbCol, String display_name) { 
		this.b = b;
		this.o = o;
		this.dbCol = dbCol;
		this.display_name = display_name;
	}
	
	public void setTracked(boolean b) { this.b = b; }
	public boolean isTracked() { return b; }
	
	public static String getSQLTableStatsTracked() { 
		ArrayList<String> cols = new ArrayList<String>();
		for (int i = 0; i < values().length; i++)
			if (values()[i].isTracked())
				cols.add(values()[i].getSQLTableCol());
		if (!cols.isEmpty())
			return String.join(" , ", cols);
		return null; 
	}
	
	public static String getSQLTableAddStats(ArrayList<String> columns) {	
		ArrayList<String> cols = new ArrayList<String>();
		columns.forEach(col -> { cols.add(valueOf(col).getSQLTableCol()); });
		if (!cols.isEmpty())
			return String.join(", ADD COLUMN ", cols);
		return null; 
	}

	public static String getSQLInsertStatsTracked() { 
		ArrayList<String> cols = new ArrayList<String>();
		for (int i = 0; i < values().length; i++)
			if (values()[i].isTracked())
				cols.add("`" + values()[i].toString() + "`");
		if (!cols.isEmpty())
			return String.join(", ", cols);
		return null; 
	}
	
	public static ArrayList<String> getSQLUpdateStatsTracked() { 
		ArrayList<String> cols = new ArrayList<String>();
		for (int i = 0; i < values().length; i++)
			if (values()[i].isTracked())
				cols.add(values()[i].toString());
		if (!cols.isEmpty())
			return cols;
		return null; 
	}
	
	public Object getDefaultValue() { return o; }
	
	public String getSQLTableCol() { return dbCol; }
	
	public String getDisplayName() { return display_name; }
}