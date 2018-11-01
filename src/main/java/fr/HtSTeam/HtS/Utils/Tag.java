package fr.HtSTeam.HtS.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Tag {
	
	public String name;
	public HashMap<String, String> attributes;
	public ArrayList<Tag> values;
	
	/**
	 * Type used in XmlFile to create tree node.
	 * <br>values = null will create a value node of the name</br>
	 * @param name Not nullable
	 * @param attributes nullable
	 * @param values nullable
	 */
	public Tag(String name, HashMap<String, String> attributes, ArrayList<Tag> values) {
		this.name = name;
		this.attributes = attributes;
		this.values = values;
	}
}