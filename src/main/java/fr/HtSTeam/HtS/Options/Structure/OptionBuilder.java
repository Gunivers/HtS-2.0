package fr.HtSTeam.HtS.Options.Structure;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;

public abstract class OptionBuilder<A> extends IconBuilder<A> implements OptionIO {
	
	private final boolean disp;

	public OptionBuilder(ItemStackBuilder material, A defaultValue, GUIBuilder gui) {
		super(material, defaultValue, gui);
		disp = true;
		this.addToList();
	}
	
	public OptionBuilder(Material material, String name, String description, A defaultValue, GUIBuilder gui) {
		super(material, name, description, defaultValue, gui);
		disp = true;
		this.addToList();
}
	
	public OptionBuilder(ItemStackBuilder material, A defaultValue, GUIBuilder gui, boolean dispInDescGUI) {
		super(material, defaultValue, gui);
		disp = dispInDescGUI;
		this.addToList();
	}
	
	public OptionBuilder(Material material, String name, String description, A defaultValue, GUIBuilder gui, boolean dispInDescGUI) {
		super(material, name, description, defaultValue, gui);
		disp = dispInDescGUI;
		this.addToList();
}

	@Override
	public abstract void event(Player p);
	public abstract void setState(A value);
	public abstract String description();
	
	@SuppressWarnings("unchecked")
	@Override
	public void load(Object o) {
		if(o.equals("true") || o.equals("false"))
			setState((A) Boolean.valueOf(o.toString()));
		else if(o.toString().matches("[0-9]+"))
			setState((A) Integer.valueOf(o.toString()));
		else setState((A) o);
	}
	
	@Override
	public ArrayList<String> save() {
		return (getValue() != null && !getValue().equals(getDefaultValue())) ? new ArrayList<String>(Arrays.asList(getValue().toString())) : null;
	}
	
	@Override
	public String getId() {
		return getName().substring(2);
	}
	
	public boolean disp() {
		return disp;
	}
	
}
