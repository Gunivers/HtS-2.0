package fr.HtSTeam.HtS.Utils;

import org.bukkit.Bukkit;

public class Nms {
	
	public static Class<?> craftPlayerClass;
	public static Class<?> entityHumanClass;
	public static Class<?> entityPlayerClass;
	
	public static Class<?> craftWorldClass;
	public static Class<?> blockPositionClass;
	public static Class<?> gamemodeEnum;

	public static Class<?> gameProfileClass;
	public static Class<?> dataWatcherClass;
	public static Class<?> playerConnectionClass;
	
	public static Class<?> packetClass;
	
	public static Class<?> packetPlayOutChatClass;
	public static Class<?> chatComponentTextClass;
	public static Class<?> iChatBaseComponentClass;
	public static Class<?> iChatBaseComponentChatSerializerClass;
	public static Class<?> chatMessageTypeClass;

	public static Class<?> packetPlayOutTitleClass;
	public static Class<?> packetPlayOutTitleEnumTitleActionClass;
	
	public static Class<?> packetPlayOutPlayerInfoClass;
	public static Class<?> packetPlayOutPlayerInfo_PlayerInfoActionEnum;
	public static Class<?> packetPlayOutPlayerInfo_PlayerInfoDataClass;
	
	public static Class<?> packetPlayOutNamedEntitySpawnClass;
	public static Class<?> packetPlayOutBedClass;
	public static Class<?> packetPlayOutEntityTeleportClass;
	public static Class<?> packetPlayOutEntityDestroyClass;
	
	public static void init() throws ClassNotFoundException
	{
		String nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		
		String nmsname = "net.minecraft.server" + nmsver + ".";
		
		
		craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
		entityHumanClass = Class.forName(nmsname + "EntityHuman");
		entityPlayerClass = Class.forName(nmsname + "EntityPlayer");
		
		craftWorldClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".CraftWorld");
		blockPositionClass = Class.forName(nmsname + "BlockPosition");
		gamemodeEnum = Class.forName(nmsname + "EnumGamemode");
		
		gameProfileClass = Class.forName("com.mojang.authlib.GameProfile");
		dataWatcherClass = Class.forName(nmsname + "DataWatcher");
		playerConnectionClass = Class.forName(nmsname + "PlayerConnection");
		
		packetClass = Class.forName(nmsname + "Packet");
		
		packetPlayOutChatClass = Class.forName(nmsname + "PacketPlayOutChat");
		chatComponentTextClass = Class.forName(nmsname + "ChatComponentText");
		iChatBaseComponentClass = Class.forName(nmsname + "IChatBaseComponent");
		iChatBaseComponentChatSerializerClass = Class.forName(nmsname + "IChatBaseComponent$ChatSerializer");
		chatMessageTypeClass = Class.forName(nmsname + "ChatMessageType");
		
		packetPlayOutTitleClass = Class.forName(nmsname + "PacketPlayOutTitle");
		packetPlayOutTitleEnumTitleActionClass = Class.forName(nmsname + "PacketPlayOutTitle$EnumTitleAction");
		
		packetPlayOutPlayerInfoClass = Class.forName(nmsname + "PacketPlayOutPlayerInfo");
		packetPlayOutPlayerInfo_PlayerInfoActionEnum = Class.forName(nmsname + "PacketPlayOutPlayerInfo$PlayerInfoActionEum");
		packetPlayOutPlayerInfo_PlayerInfoDataClass = Class.forName(nmsname + "PacketPlayOutPlayerInfo$PlayerInfoData");
		
		packetPlayOutNamedEntitySpawnClass = Class.forName(nmsname + "PacketPlayOutNamedEntitySpawn");
		packetPlayOutBedClass = Class.forName(nmsname + "PacketPlayOutBed");
		packetPlayOutEntityTeleportClass = Class.forName(nmsname + "PacketPlayOutEntityTeleport");
		packetPlayOutEntityDestroyClass = Class.forName(nmsname + "PacketPlayOutEntityDestroy");
	}
}