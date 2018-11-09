package fr.HtSTeam.HtS.Options.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Files.OptionIO;

public abstract class Option<A> extends Icon implements OptionIO {
	
	public static Map<Option<?>, Object> optionsList = new HashMap<>();
	private boolean disp;
	private final A defaultValue;
	protected A value;

	/**
	 * 	 * @param defaultValue
	 * 		Valeur par défaut. Sert de comparaison à la valeur actuel pour afficher ou nom le changement au récapitulatif du /start
	 * @param material
	 * @param defaultValue
	 * @param gui
	 */
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
		value = defaultValue;
		this.addToList();
		disp = true;
		Main.LOGGER.logInfo("[Options] > " + getClass().getSimpleName() + "    ...    " + getClass().getName());
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
		return (value != null && !value.equals(getDefaultValue())) ? new ArrayList<String>(Arrays.asList(value.toString())) : null;
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
}
