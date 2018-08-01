package fr.HtSTeam.HtS.Utils.Files;

import java.util.ArrayList;

public interface OptionIO {
	
	static ArrayList<OptionIO> optionIOClass = new ArrayList<OptionIO>();
	
	public void load(Object o);
	public ArrayList<String> save();
	public String getId();
	
	public default void addToList() {
		optionIOClass.add(this);
	}
}
