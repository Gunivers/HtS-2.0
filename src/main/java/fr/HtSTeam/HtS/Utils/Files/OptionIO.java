package fr.HtSTeam.HtS.Utils.Files;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public interface OptionIO {
	
	static Set<OptionIO> optionIOClass = new HashSet<OptionIO>();
	
	public void load(Object o);
	public ArrayList<String> save();
	public String getId();
	
	public default void addToList() {
		optionIOClass.add(this);
	}
}
