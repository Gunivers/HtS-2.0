package fr.HtSTeam.HtS.Utils;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class ItemStackBuilder extends ItemStack {

	private Material item;
	private String name, lore;
	private short data;
	private int number;
	private boolean glint;

	
	/**
	 * @param item Type d'item
	 * @param data Data de l'item
	 * @param number Nombre
	 * @param name Nom de l'item
	 * @param lore Description de l'item
	 */
	public ItemStackBuilder(Material item, short data, int number, String name, String lore) {
		super(item, number, data);
		setLore(lore);
		setName(name);
		glint = false;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public ItemStackBuilder(Material item, short data, int number, String name, String lore, boolean glint) {
		this.item = item;
		this.data = data;
		this.name = name;
		this.number = number;
		this.lore = lore;
		this.glint = glint;
	}
	
	/**
	 * Génère un oeuf
	 * @param entity l'entité de l'oeuf
	 * @param number Nombre
	 * @param name Nom de l'item
	 * @param lore Description de l'item
	 */
	public ItemStackBuilder(EntityType entity, int number, String name, String lore) {
		super(Material.MONSTER_EGG, number, (short) 0);
		SpawnEggMeta sem = (SpawnEggMeta) getItemMeta();
		sem.setSpawnedType(entity);
		setItemMeta(sem);
		setLore(lore);
		setName(name);
		glint = false;
	}	
	
	/**
	 * Génère une tête
	 * @param player un UUID
	 * @param number Nombre
	 * @param name Nom de l'item
	 * @param lore Description de l'item
	 */
	public ItemStackBuilder(UUID player, int number, String name, String lore) {
		super(Material.SKULL, number, (short) 3);
		SkullMeta sm = (SkullMeta) getItemMeta();
		sm.setOwningPlayer(Bukkit.getOfflinePlayer(player));
		setItemMeta(sm);
		setLore(lore);
		setName(name);
		glint = false;
	}	

	/**
	 * @return le nom de l'item
	 */
	public String getName() {
		return getItemMeta().getDisplayName();
	}

	@SuppressWarnings("deprecation")
	/**
	 * @return le datavalue de l'item
	 */
	public short getDataValue() {
		return getData().getData();
	}

	/**
	 * @return la description de l'item
	 */
	public String getLore() {
		return getItemMeta().getLore().get(0);
	}

	/**
	 * @return si l'item à l'effet de surbrillance
	 */
	public boolean isGlint() {
		return glint;
	}

	/**
	 * @param name le nom de l'item
	 */
	public void setName(String name) {
		ItemMeta ism = getItemMeta();
		ism.setDisplayName(name);
		setItemMeta(ism);
	}

	@Deprecated
	/**
	 * @param data datavalue de l'item
	 */
	public void setDataValue(short data) {
		this.data = data;
	}

	/**
	 * @param lore description de l'item
	 */
	public void setLore(String lore) {
		ItemMeta ism = getItemMeta();
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(lore);
		ism.setLore(lores);
		setItemMeta(ism);
	}

	/**
	 * @param glint l'item est en subrillance
	 */
	public void setGlint(boolean glint) {
		this.glint = glint;
		ItemMeta ism = getItemMeta();
		if (glint) {
			ism.addEnchant(Enchantment.DURABILITY, 1, false);
			ism.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		} else {
			ism.removeEnchant(Enchantment.DURABILITY);
		}
		setItemMeta(ism);
	}

	/**
	 * @param material item de l'item
	 * @param data datavalue de l'item
	 */
	public void setItem(Material material, short data) {
		this.item = material;
		this.data = data;
		this.setType(material);
		this.setDataValue(data);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
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
}
