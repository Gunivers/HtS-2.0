package fr.HtSTeam.HtS.Utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class ActionBar {

	private Player p;
	private String msg;
	private int time;
	
	private int id;
	
	public ActionBar(String msg, int time) {
		this.msg = msg;
		this.time = time;
	}
	public ActionBar(Player p, String msg, int time) {
		this.p = p;
		this.msg = msg;
		this.time = time;
	}
	
	/**
	 * Envoie le message à tout les joueurs.
	 */
	public void sendAll() {
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
	
	/**
	 * Envoie le message au joueur précisé dans le constructeur.
	 */
	public void send() {
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