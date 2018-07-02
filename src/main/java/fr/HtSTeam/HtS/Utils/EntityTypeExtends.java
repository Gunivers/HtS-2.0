package fr.HtSTeam.HtS.Utils;

import java.util.ArrayList;

import org.bukkit.entity.EntityType;

public class EntityTypeExtends {
	
	public static boolean spawnableByEgg(EntityType e) {
		switch(e) {
			case BAT:
			case BLAZE:
			case CAVE_SPIDER:
			case CHICKEN:
			//case COD:
			case COW:
			case CREEPER:
			//case DOLPHIN:
			case DONKEY:
			//case DROWNED:
			case ELDER_GUARDIAN:
			case ENDERMAN:
			case ENDERMITE:
			case EVOKER:
			case GHAST:
			case GUARDIAN:
			case HORSE:
			case HUSK:
			case LLAMA:
			case MAGMA_CUBE:
			case MUSHROOM_COW:
			case OCELOT:
			case PARROT:
			//case PHANTOM:
			case PIG:
			case PIG_ZOMBIE:
			case POLAR_BEAR:
			//case PUFFERFFISH:
			case RABBIT:
				//case SALMON:
			case SHEEP:
			case SHULKER:
			case SILVERFISH:
			case SKELETON:
			case SKELETON_HORSE:
			case SLIME:
			case SPIDER:
			case SQUID:
			case STRAY:
			//case TROPICAL_FISH:
			//case TURTLE:
			case VEX:
			case VILLAGER:
			case VINDICATOR:
			case WITCH:
			case WITHER_SKELETON:
			case WOLF:
			case ZOMBIE:
			case ZOMBIE_HORSE:
			case ZOMBIE_VILLAGER:
				return true;
			default:
				return false;
				
		}
	}
	
	public static ArrayList<EntityType> getEntitySpawnableByEgg() {
		ArrayList<EntityType> list = new ArrayList<EntityType>();
		list.add(EntityType.BAT);
		list.add(EntityType.BLAZE);
		list.add(EntityType.CAVE_SPIDER);
		list.add(EntityType.CHICKEN);
		//list.add(EntityType.COD);
		list.add(EntityType.COW);
		list.add(EntityType.CREEPER);
		//list.add(EntityType.DOLPHIN);
		list.add(EntityType.DONKEY);
		//list.add(EntityType.DROWNED);
		list.add(EntityType.ELDER_GUARDIAN);
		list.add(EntityType.ENDERMAN);
		list.add(EntityType.ENDERMITE);
		list.add(EntityType.EVOKER);
		list.add(EntityType.GHAST);
		list.add(EntityType.GUARDIAN);
		list.add(EntityType.HORSE);
		list.add(EntityType.HUSK);
		list.add(EntityType.LLAMA);
		list.add(EntityType.MAGMA_CUBE);
		list.add(EntityType.MUSHROOM_COW);
		list.add(EntityType.OCELOT);
		list.add(EntityType.PARROT);
		//list.add(EntityType.PHANTOM);
		list.add(EntityType.PIG);
		list.add(EntityType.PIG_ZOMBIE);
		list.add(EntityType.POLAR_BEAR);
		//list.add(EntityType.PUFFERFFISH);
		list.add(EntityType.RABBIT);
			//list.add(EntityType.SALMON);
		list.add(EntityType.SHEEP);
		list.add(EntityType.SHULKER);
		list.add(EntityType.SILVERFISH);
		list.add(EntityType.SKELETON);
		list.add(EntityType.SKELETON_HORSE);
		list.add(EntityType.SLIME);
		list.add(EntityType.SPIDER);
		list.add(EntityType.SQUID);
		list.add(EntityType.STRAY);
		//list.add(EntityType.TROPICAL_FISH);
		//list.add(EntityType.TURTLE);
		list.add(EntityType.VEX);
		list.add(EntityType.VILLAGER);
		list.add(EntityType.VINDICATOR);
		list.add(EntityType.WITCH);
		list.add(EntityType.WITHER_SKELETON);
		list.add(EntityType.WOLF);
		list.add(EntityType.ZOMBIE);
		list.add(EntityType.ZOMBIE_HORSE);
		list.add(EntityType.ZOMBIE_VILLAGER);
		return list;
	}

}
