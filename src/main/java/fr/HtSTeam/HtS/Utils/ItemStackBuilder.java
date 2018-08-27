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
		this(mobToEgg(entity), number, name, lore);

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

	@Deprecated

	/**
	 * @param lore
	 *            description de l'item
	 */
	public void setLore(String lore) {
		ItemMeta ism = getItemMeta();
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(lore);
		ism.setLore(lores);
		setItemMeta(ism);
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

	private static Material mobToEgg(EntityType et) {
		switch (et) {
		case ELDER_GUARDIAN:
			return Material.ELDER_GUARDIAN_SPAWN_EGG;
		case WITHER_SKELETON:
			return Material.WITHER_SKELETON_SPAWN_EGG;
		case STRAY:
			return Material.STRAY_SPAWN_EGG;
		case HUSK:
			return Material.HUSK_SPAWN_EGG;
		case ZOMBIE_VILLAGER:
			return Material.ZOMBIE_VILLAGER_SPAWN_EGG;
		case SKELETON_HORSE:
			return Material.SKELETON_HORSE_SPAWN_EGG;
		case ZOMBIE_HORSE:
			return Material.ZOMBIE_HORSE_SPAWN_EGG;
		case DONKEY:
			return Material.DONKEY_SPAWN_EGG;
		case MULE:
			return Material.MULE_SPAWN_EGG;
		case EVOKER:
			return Material.EVOKER_SPAWN_EGG;
		case VEX:
			return Material.VEX_SPAWN_EGG;
		case VINDICATOR:
			return Material.VINDICATOR_SPAWN_EGG;
		case CREEPER:
			return Material.CREEPER_SPAWN_EGG;
		case SKELETON:
			return Material.SKELETON_SPAWN_EGG;
		case SPIDER:
			return Material.SPIDER_SPAWN_EGG;
		case ZOMBIE:
			return Material.ZOMBIE_SPAWN_EGG;
		case SLIME:
			return Material.SLIME_SPAWN_EGG;
		case GHAST:
			return Material.GHAST_SPAWN_EGG;
		case PIG_ZOMBIE:
			return Material.ZOMBIE_PIGMAN_SPAWN_EGG;
		case ENDERMAN:
			return Material.ENDERMAN_SPAWN_EGG;
		case CAVE_SPIDER:
			return Material.CAVE_SPIDER_SPAWN_EGG;
		case SILVERFISH:
			return Material.SILVERFISH_SPAWN_EGG;
		case BLAZE:
			return Material.BLAZE_SPAWN_EGG;
		case MAGMA_CUBE:
			return Material.MAGMA_CUBE_SPAWN_EGG;
		case BAT:
			return Material.BAT_SPAWN_EGG;
		case WITCH:
			return Material.WITCH_SPAWN_EGG;
		case ENDERMITE:
			return Material.ENDERMITE_SPAWN_EGG;
		case GUARDIAN:
			return Material.GUARDIAN_SPAWN_EGG;
		case SHULKER:
			return Material.SHULKER_SPAWN_EGG;
		case PIG:
			return Material.PIG_SPAWN_EGG;
		case SHEEP:
			return Material.SHEEP_SPAWN_EGG;
		case COW:
			return Material.COW_SPAWN_EGG;
		case CHICKEN:
			return Material.CHICKEN_SPAWN_EGG;
		case SQUID:
			return Material.SQUID_SPAWN_EGG;
		case WOLF:
			return Material.WOLF_SPAWN_EGG;
		case MUSHROOM_COW:
			return Material.MOOSHROOM_SPAWN_EGG;
		case OCELOT:
			return Material.OCELOT_SPAWN_EGG;
		case HORSE:
			return Material.HORSE_SPAWN_EGG;
		case RABBIT:
			return Material.RABBIT_SPAWN_EGG;
		case POLAR_BEAR:
			return Material.POLAR_BEAR_SPAWN_EGG;
		case LLAMA:
			return Material.LLAMA_SPAWN_EGG;
		case PARROT:
			return Material.PARROT_SPAWN_EGG;
		case VILLAGER:
			return Material.VILLAGER_SPAWN_EGG;
		case TURTLE:
			return Material.TURTLE_SPAWN_EGG;
		case PHANTOM:
			return Material.PHANTOM_SPAWN_EGG;
		case COD:
			return Material.COD_SPAWN_EGG;
		case SALMON:
			return Material.SALMON_SPAWN_EGG;
		case PUFFERFISH:
			return Material.PUFFERFISH_SPAWN_EGG;
		case TROPICAL_FISH:
			return Material.TROPICAL_FISH_SPAWN_EGG;
		case DROWNED:
			return Material.DROWNED_SPAWN_EGG;
		case DOLPHIN:
			return Material.DOLPHIN_SPAWN_EGG;
		default:
			return null;
		}
	}
}
