package fr.HtSTeam.HtS.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStackBuilder extends ItemStack {

	private boolean glint;

	/**
	 * @param item
	 *            Type d'item
	 * @param number
	 *            Nombre
	 * @param name
	 *            Nom de l'item
	 * @param lore
	 *            Description de l'item
	 */
	public ItemStackBuilder(Material item, int number, String name, String lore) {
		super(item, number);
		setName(name);
		setLore(lore);
		glint = false;
	}

	/**
	 * Génère un oeuf
	 * 
	 * @param entity
	 *            l'entité de l'oeuf
	 * @param number
	 *            Nombre
	 * @param name
	 *            Nom de l'item
	 * @param lore
	 *            Description de l'item
	 */
	public ItemStackBuilder(EntityType entity, int number, String name, String lore) {
		this(MobManager.mobToEgg(entity), number, name, lore);

	}

	/**
	 * Génère une tête
	 * 
	 * @param player
	 *            un UUID
	 * @param number
	 *            Nombre
	 * @param name
	 *            Nom de l'item
	 * @param lore
	 *            Description de l'item
	 */
	public ItemStackBuilder(UUID player, int number, String name, String lore) {
		super(Material.PLAYER_HEAD, number);
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
	 * @param name
	 *            le nom de l'item
	 */
	public void setName(String name) {
		ItemMeta ism = getItemMeta();
		ism.setDisplayName(name);
		setItemMeta(ism);
	}

	/**
	 * @param lore
	 *            description de l'item
	 */
	public void setLore(String... lore) {
		ItemMeta ism = getItemMeta();
		if(lore.length == 1 && lore[0].equals("")) {
			ism.setLore(null);
		} else {
			ArrayList<String> lores = new ArrayList<String>();
			lores.addAll(Arrays.asList(lore));
			ism.setLore(lores);
			setItemMeta(ism);
		}
	}

	/**
	 * @param glint
	 *            l'item est en subrillance
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
	 * @param material
	 *            item de l'item
	 */
	public void setItem(Material material) {
		this.setType(material);
	}
}
