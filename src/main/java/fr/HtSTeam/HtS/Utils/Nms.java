package fr.HtSTeam.HtS.Utils;

import org.bukkit.Bukkit;

public class Nms {
	
	public static Class<?> craftPlayerClass;
	public static Class<?> entityHumanClass;
	
	public static Class<?> craftWorldClass;
	public static Class<?> blockPositionClass;
	public static Class<?> gamemodeEnum;

	public static Class<?> gameProfileClass;
	public static Class<?> dataWatcherClass;
	
	public static Class<?> packetClass;
	
	public static Class<?> packetPlayOutChatClass;
	public static Class<?> chatComponentTextClass;
	public static Class<?> iChatBaseComponentClass;
	public static Class<?> iChatBaseComponentChatSerializerClass;
	public static Class<?> chatMessageTypeClass;

	public static Class<?> packetPlayOutTitleClass;
	public static Class<?> packetPlayOutTitleEnumTitleActionClass;
	
	public static Class<?> packetPlayOutNamedEntitySpawnClass;
	public static Class<?> packetPlayOutBedClass;
	public static Class<?> packetPlayOutEntityTeleportClass;
	public static Class<?> packetPlayOutEntityDestroyClass;
	
	/**
	 * Fetches all needed class for NMS.
	 * @throws ClassNotFoundException
	 */
	public static void init() throws ClassNotFoundException
	{
		String nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		
		String nmsPackage = "net.minecraft.server." + nmsver + ".";
		
		craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
		entityHumanClass = Class.forName(nmsPackage + "EntityHuman");
//		entityPlayerClass = Class.forName(nmsname + "EntityPlayer");
		
		craftWorldClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".CraftWorld");
		blockPositionClass = Class.forName(nmsPackage + "BlockPosition");
		gamemodeEnum = Class.forName(nmsPackage + "EnumGamemode");
		
		dataWatcherClass = Class.forName(nmsPackage + "DataWatcher");
//		playerConnectionClass = Class.forName(nmsname + "PlayerConnection");
		
		packetClass = Class.forName(nmsPackage + "Packet");
		
		packetPlayOutChatClass = Class.forName(nmsPackage + "PacketPlayOutChat");
		chatComponentTextClass = Class.forName(nmsPackage + "ChatComponentText");
		iChatBaseComponentClass = Class.forName(nmsPackage + "IChatBaseComponent");
		iChatBaseComponentChatSerializerClass = Class.forName(nmsPackage + "IChatBaseComponent$ChatSerializer");
		chatMessageTypeClass = Class.forName(nmsPackage + "ChatMessageType");
		
		packetPlayOutTitleClass = Class.forName(nmsPackage + "PacketPlayOutTitle");
		packetPlayOutTitleEnumTitleActionClass = Class.forName(nmsPackage + "PacketPlayOutTitle$EnumTitleAction");
		
//		packetPlayOutPlayerInfoClass = Class.forName(nmsname + "PacketPlayOutPlayerInfo");
//		packetPlayOutPlayerInfo_PlayerInfoActionEnum = Class.forName(nmsname + "PacketPlayOutPlayerInfo$PlayerInfoActionEum");
//		packetPlayOutPlayerInfo_PlayerInfoDataClass = Class.forName(nmsname + "PacketPlayOutPlayerInfo$PlayerInfoData");
		
		packetPlayOutNamedEntitySpawnClass = Class.forName(nmsPackage + "PacketPlayOutNamedEntitySpawn");
		packetPlayOutBedClass = Class.forName(nmsPackage + "PacketPlayOutBed");
		packetPlayOutEntityTeleportClass = Class.forName(nmsPackage + "PacketPlayOutEntityTeleport");
		packetPlayOutEntityDestroyClass = Class.forName(nmsPackage + "PacketPlayOutEntityDestroy");
	}
}