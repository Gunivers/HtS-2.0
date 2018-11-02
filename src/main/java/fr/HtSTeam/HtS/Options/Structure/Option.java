package fr.HtSTeam.HtS.Options.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;

public abstract class Option<A> extends Icon implements OptionIO {
	
	public static Map<Option<?>, Object> optionsList = new HashMap<>();
	private boolean disp;
	private final A defaultValue;
	private A value;

	public Option(ItemStackBuilder material, A defaultValue, Gui gui) {
		this(material, defaultValue, gui, -1);
	}
	

	public Option(Material material, String name, String description, A defaultValue, Gui gui) {
		this(material, name, description, defaultValue, gui, -1);
	}
	
	public Option(Material material, String name, String description, A defaultValue, Gui gui, int slot) {
		this(new ItemStackBuilder(material, 1, "§r" + name, description), defaultValue, gui, slot);
	}

	public Option(ItemStackBuilder material, A defaultValue, Gui gui, int slot) {
		super(material, gui, slot);
		this.defaultValue = defaultValue;
		setValue(defaultValue);
		this.addToList();
		disp = true;
	}
	

	@Override
	public abstract void event(Player p);
	public abstract void setState(A value);
	public abstract String getDescription();
	
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
	
	public boolean mustBeDisplayed() {
		return disp;
	}
	
	public void disp(boolean disp) {
		this.disp = disp;
	}

	public A getDefaultValue() {
		return defaultValue;
	}

	public A getValue() {
		return value;
	}

	public void setValue(A value) {
		this.value = value;
	}
	
}
