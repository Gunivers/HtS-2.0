package fr.HtSTeam.HtS.Utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class ActionBar {
	
	private static int id;
	
	public static void send(String msg, int time) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR,
				ChatSerializer.a("{\"text\":\"" + msg + "\"}"));
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable(){
			private int counter = 0;

			@Override
			public void run(){	 
					for(Player p : Bukkit.getOnlinePlayers())
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					counter++;
					if(counter == time)
						Bukkit.getScheduler().cancelTask(id);
			 } 
		}, 0L, 20L);
	}
	
	public static void send(Player p, String msg, int time) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR,
				ChatSerializer.a("{\"text\":\"" + msg + "\"}"));
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable(){
			private int counter = 0;

			@Override
			public void run(){	 
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					counter++;
					if(counter == time)
						Bukkit.getScheduler().cancelTask(id);
			 } 
		}, 0L, 20L);
	} 
}