package fr.HtSTeam.HtS.Players;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.WorldServer;

public class RealisticDeath implements Listener {
	
//	private static HashMap<PacketPlayOutPlayerInfo,PacketPlayOutNamedEntitySpawn> pi_spawn = new HashMap<PacketPlayOutPlayerInfo,PacketPlayOutNamedEntitySpawn>();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(PlayerDeathEvent e) {
		realisticDeath(e.getEntity(), e.getEntity().getLocation());
	}
	
//	@EventHandler
//	public void onRespawn(PlayerRespawnEvent e) {
//		EntityPlayer player = ((CraftPlayer) e.getPlayer()).getHandle();
//		for (PacketPlayOutPlayerInfo pi : pi_spawn.keySet()) {
//			player.playerConnection.sendPacket(pi);
//			player.playerConnection.sendPacket(pi_spawn.get(pi));
//		}
//	}
	
	private void realisticDeath(Player p, Location l) {
		MinecraftServer mcServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) p.getWorld()).getHandle();

		GameProfile gp = new GameProfile(p.getUniqueId(), "§aDead");
					
		EntityPlayer fake = new EntityPlayer(mcServer, nmsWorld, gp, new PlayerInteractManager(nmsWorld));
		fake.setLocation(l.getX(), l.getY(), l.getZ(), 90F, 0F);
		fake.setSilent(true);
		
//		pi_spawn.put(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, fake), new PacketPlayOutNamedEntitySpawn(fake));
		
		EntityPlayer player = ((CraftPlayer) p).getHandle();
		player.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, fake));
		player.playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(fake));
	}
}
