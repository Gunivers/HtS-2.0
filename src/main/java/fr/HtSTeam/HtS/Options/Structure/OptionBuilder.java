package fr.HtSTeam.HtS.Options.Structure;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.OptionIO;

public abstract class OptionBuilder<A> extends IconBuilder<A> implements OptionIO {
	
	private final boolean disp;

	public OptionBuilder(ItemStackBuilder material, A defaultValue, GUIBuilder gui) {
		super(material, defaultValue, gui);
		disp = true;
	}
	
	public OptionBuilder(Material material, String name, String description, A defaultValue, GUIBuilder gui) {
		super(material, name, description, defaultValue, gui);
		disp = true;
}
	
	public OptionBuilder(ItemStackBuilder material, A defaultValue, GUIBuilder gui, boolean dispInDescGUI) {
		super(material, defaultValue, gui);
		disp = dispInDescGUI;
	}
	
	public OptionBuilder(Material material, String name, String description, A defaultValue, GUIBuilder gui, boolean dispInDescGUI) {
		super(material, name, description, defaultValue, gui);
		disp = dispInDescGUI;
}

	@Override
	public abstract void event(Player p);
	public abstract void setState(A value);
	public abstract String description();
	
	@SuppressWarnings("unchecked")
	@Override
	public void load(Object o) {
		if(this.getValue().getClass().isInstance(o.getClass()))
			setValue((A) o);
	}
	
	@Override
	public List<String> save() {
		return Arrays.asList(getValue().toString());
	}
	
	public boolean disp() {
		return disp;
	}
	
}
