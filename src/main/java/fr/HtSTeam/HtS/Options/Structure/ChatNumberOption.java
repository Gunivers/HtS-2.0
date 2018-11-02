package fr.HtSTeam.HtS.Options.Structure;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public abstract class ChatNumberOption extends ChatOption<Integer> {

	private final Integer min;
	private final Integer max;
	
	public ChatNumberOption(ItemStackBuilder material, Integer defaultValue, Gui gui, Integer min, Integer max) {
		this(material, defaultValue, gui, -1, min, max);
	}
	
	public ChatNumberOption(Material material, String name, String description, Integer defaultValue, Gui gui, Integer min, Integer max) {
		this(material, name, description, defaultValue, gui, -1, min, max);
	}
	
	public ChatNumberOption(Material material, String name, String description, Integer defaultValue, Gui gui, int slot, Integer min, Integer max) {
		this(new ItemStackBuilder(material, 1, "§r" + name, description), defaultValue, gui, slot, min, max);
	}
	
	public ChatNumberOption(ItemStackBuilder material, Integer defaultValue, Gui gui, int slot, Integer min, Integer max) {
		super(material, defaultValue, gui, slot);
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean isCorrectValue(String value) {
		try {
			int val = Integer.parseInt(value);
			if(val < min || val > max)
				p.sendMessage("Valeur non comprise entre " + min + " et " + max + ".");
			else {
				setValue(val);
				dispValidMessage();
				return true;
			}
		} catch(NumberFormatException e) {
			p.sendMessage("Valeur invalide.");
		}
		return false;
	}
	
	public abstract void dispValidMessage();
}
