package fr.HtSTeam.HtS;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemStackManager {
	
	private Material item;
	private String name, lore;
	private short data;
	private int number;
	private boolean glint;

	
	public ItemStackManager(Material item, short data, int number, String name, String lore, boolean glint) {
		this.item = item;
		this.data = data;
		this.name = name;
		this.number = number;
		this.lore = lore;
		this.glint = glint;
	}
	
	public String getName() { return name; }
	public short getData() { return data; }
	public String getLore() { return lore; }
	public int getAmount() { return number; }
	public Material getMaterial() { return item; }
	
	public boolean isGlint() { return glint; }
	
	public void setName(String name) { this.name = name; }
	public void setshort(short data) { this.data = data; }
	public void setLore(String lore) { this.lore = lore; }
	public void setAmount(int amount) { this.number = amount; }
	public void setGlint(boolean glint) { this.glint = glint; }
	public void setItem(Material material, short data) { this.item = material; this.data = data; }
	
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(this.item, this.number, (short) this.data);
		ItemMeta isM = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(this.lore);
		isM.setDisplayName(this.name);
		if (this.lore != null)
			isM.setLore(lore);
		if (this.glint == true) {
			isM.addEnchant(Enchantment.DURABILITY, 1, false);
			isM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		}
		is.setItemMeta(isM);
		return is;
	}


	public boolean getGlint() {
		return glint;
	}
}
