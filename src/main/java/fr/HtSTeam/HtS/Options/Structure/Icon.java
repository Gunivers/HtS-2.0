package fr.HtSTeam.HtS.Options.Structure;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public abstract class Icon {
	
	private ItemStackBuilder icon;
	private Gui parent;
	
	/**
	 * @param material
	 * 		Item représentant l'option dans le menu 
	 * @param name
	 * 		Nom de l'item
	 * @param description
	 * 		Description de l'item
	 * @param gui
	 * 		Inventaire où sera placé l'item
	 * @param slot
	 * 		Slot de l'inventaire dans lequel sera placé l'item. L'entier doit être plus petit que {@link Gui#getSize()}.
	 */
	protected Icon(Material material, String name, String description, Gui gui, int slot) {
		this(new ItemStackBuilder(material, 1, "§r" + name, description), gui, slot);
	}
	
	protected Icon(Material material, String name, String description, Gui gui) {
		this(new ItemStackBuilder(material, 1, "§r" + name, description), gui);
	}
	
	/**
	 * @param material un ItemStackBuilder
	 * @param gui
	 * 		Inventaire où sera placé l'item
	 */
	protected Icon(ItemStackBuilder material, Gui gui) {
		this(material, gui, -1);
	}
	
	protected Icon(ItemStackBuilder material, Gui gui, int slot) {
			parent = gui;
			if(material.getLore() != null)
				material.setLore("§r" + material.getLore());
			this.icon = material;
			if(slot == -1)
				gui.put(this);
			else
				gui.put(this, slot);
	}
	
	public abstract void event(Player p);
	
	public String getIconDescription() { return icon.getLore(); }
	public String getName() { return icon.getName(); }
	public ItemStackBuilder getItemStack() { return icon; }
	
	public Gui getParent() { return parent; }
}
