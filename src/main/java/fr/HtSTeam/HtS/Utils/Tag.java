package fr.HtSTeam.HtS.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Tag {
	
	public String name;
	public HashMap<String, String> attributes;
	public ArrayList<Tag> values;
	
	public Tag(String name, HashMap<String, String> attributes, ArrayList<Tag> values) {
		this.name = name;
		this.attributes = attributes;
		this.values = values;
	}
}