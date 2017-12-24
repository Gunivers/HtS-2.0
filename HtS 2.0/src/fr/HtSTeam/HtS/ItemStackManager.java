package fr.HtSTeam.HtS;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;


public class ItemStackManager {
	
	private Material item;
	private String name, lore;
	private short data;
	private int number;
	private boolean glint, level;
	private PotionType potionType;

	
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
	
	public ItemStack getItemStack() {
		if (item != Material.POTION) {
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
		} else {
			ItemStack potion = new ItemStack(item, 1);
			PotionMeta potionM = (PotionMeta) potion.getItemMeta();
			potionM.setBasePotionData(new PotionData(potionType, false, level));
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(this.lore);
			potionM.setDisplayName(this.name);
			if (this.lore != null)
				potionM.setLore(lore);
			if (this.glint == true) {
				potionM.addEnchant(Enchantment.DURABILITY, 1, false);
				potionM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
			}
			potion.setItemMeta(potionM);
			return potion;
		}
	}


	public boolean getGlint() {
		return glint;
	}
}
