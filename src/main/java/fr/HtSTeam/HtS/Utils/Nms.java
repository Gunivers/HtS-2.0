package fr.HtSTeam.HtS.Utils;

import org.bukkit.Bukkit;

public class Nms {
	
	public static Class<?> craftPlayerClass;
	
	public static Class<?> packetClass;
	
	public static Class<?> packetPlayOutChatClass;
	public static Class<?> chatComponentTextClass;
	public static Class<?> iChatBaseComponentClass;
	public static Class<?> iChatBaseComponentChatSerializerClass;
	public static Class<?> chatMessageTypeClass;

	public static Class<?> packetPlayOutTitleClass;
	public static Class<?> packetPlayOutTitleEnumTitleActionClass;
	
	public static void init() throws ClassNotFoundException {
		String nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		
		craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
		
		packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");
		
		packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
		chatComponentTextClass = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
		iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
		iChatBaseComponentChatSerializerClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent$ChatSerializer");
		chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
		
		packetPlayOutTitleClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutTitle");
		packetPlayOutTitleEnumTitleActionClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutTitle$EnumTitleAction");
	}
	
}