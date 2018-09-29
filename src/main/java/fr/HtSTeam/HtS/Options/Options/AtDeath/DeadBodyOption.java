package fr.HtSTeam.HtS.Options.Options.AtDeath;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.mojang.authlib.GameProfile;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.ChatMessage;
import net.minecraft.server.v1_13_R2.DataWatcher;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.EnumGamemode;
import net.minecraft.server.v1_13_R2.MathHelper;
import net.minecraft.server.v1_13_R2.PacketPlayOutBed;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_13_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo;

/**
 * @author SwiftLee
 * @author A~Z
 */
public class DeadBodyOption extends OptionBuilder<Boolean> implements EndTrigger
{
	private Integer entityID = 0;
	private HashMap<Player, Entry<Location, Integer>> corpses = new HashMap<>();
	
	public DeadBodyOption()
	{
		super(Material.BONE, "Cadavres", "§2Activé", true, GUIRegister.atDeath, false);
	}

	@Override
	public void event(Player p)
	{
		this.setState(!this.getValue());
	}

	@Override
	public void setState(Boolean value)
	{
		this.setValue(value);
		
		if (value)
			this.getItemStack().setLore("§2Activé");
		else
			this.getItemStack().setLore("§4Désactivé");
	}

	@Override
	public void onPartyEnd()
	{
		for (Player p : corpses.keySet())
		{
			this.destroyCorpse(p);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDie(PlayerDeathEvent event)
	{
		if (!this.getValue()) return;
		
		try
		{
			this.spawnCorpse(event.getEntity());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	private void spawnCorpse(Player p) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		BlockPosition pos = new BlockPosition(p.getLocation().getBlockX(), 0, p.getLocation().getBlockZ());
		this.spawnCorpse(p, pos);
	}
	  
	@SuppressWarnings("deprecation")
	private void spawnCorpse(Player p, BlockPosition pos) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		HashMap<Location, Integer> temp = new HashMap<>();
		temp.put(p.getLocation(), entityID.intValue());		//entityID.intValue() clones the instance so as to avoid synchronization issues.
		corpses.put(p, temp.entrySet().iterator().next());
		
		CraftPlayer p1 = (CraftPlayer) p;

		double locY = ((EntityHuman) p1.getHandle()).locY;

		DataWatcher dw = clonePlayerDatawatcher(p, entityID);
		//dw.watch(10, p1.getHandle().getDataWatcher().getByte(10));

		GameProfile prof = new GameProfile(p1.getUniqueId(), p1.getName());

		PacketPlayOutPlayerInfo packetInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
		PacketPlayOutPlayerInfo.PlayerInfoData data = packetInfo.new PlayerInfoData(prof, 0, EnumGamemode.SURVIVAL, new ChatMessage("", new Object[0]));
	    
		List<PacketPlayOutPlayerInfo.PlayerInfoData> dataList = new ArrayList<>();
	    
		dataList.add(data);
		setValue(packetInfo, "b", dataList);

	    PacketPlayOutNamedEntitySpawn packetEntitySpawn = new PacketPlayOutNamedEntitySpawn();
	    setValue(packetEntitySpawn, "a", entityID);
	    setValue(packetEntitySpawn, "b", prof.getId());
	    setValue(packetEntitySpawn, "c", MathHelper.floor(((EntityHuman) p1.getHandle()).locX * 32D));
	    setValue(packetEntitySpawn, "d", MathHelper.floor(locY * 32D));
	    setValue(packetEntitySpawn, "e", MathHelper.floor(((EntityHuman) p1.getHandle()).locZ * 32D));
	    setValue(packetEntitySpawn, "f", (byte) ((int) (((EntityHuman) p1.getHandle()).yaw * 256.0F / 360.0F)));
	    setValue(packetEntitySpawn, "g", (byte) ((int) (((EntityHuman) p1.getHandle()).pitch * 256.0F / 360.0F)));
	    setValue(packetEntitySpawn, "i", dw);

	    PacketPlayOutBed packetBed = new PacketPlayOutBed();
	    setValue(packetBed, "a", entityID);
	    setValue(packetBed, "b", pos);

	    PacketPlayOutEntityTeleport packetTeleport = new PacketPlayOutEntityTeleport();
	    setValue(packetTeleport, "a", entityID);
	    setValue(packetTeleport, "b", MathHelper.floor(((EntityHuman) p1.getHandle()).locX * 32.0D));
	    setValue(packetTeleport, "c", MathHelper.floor(locY * 32.0D));
	    setValue(packetTeleport, "d", MathHelper.floor(((EntityHuman) p1.getHandle()).locZ * 32.0D));
	    setValue(packetTeleport, "e", (byte) ((int) (((EntityHuman) p1.getHandle()).yaw * 256.0F / 360.0F)));
	    setValue(packetTeleport, "f", (byte) ((int) (((EntityHuman) p1.getHandle()).pitch * 256.0F / 360.0F)));
	    setValue(packetTeleport, "g", true);

	    PacketPlayOutEntityTeleport packetTeleportDown = new PacketPlayOutEntityTeleport();
	    setValue(packetTeleportDown, "a", entityID);
	    setValue(packetTeleportDown, "b", MathHelper.floor(((EntityHuman) p1.getHandle()).locX * 32.0D));
	    setValue(packetTeleportDown, "c", 0);
	    setValue(packetTeleportDown, "d", MathHelper.floor(((EntityHuman) p1.getHandle()).locZ * 32.0D));
	    setValue(packetTeleportDown, "e", (byte) ((int) (((EntityHuman) p1.getHandle()).yaw * 256.0F / 360.0F)));
	    setValue(packetTeleportDown, "f", (byte) ((int) (((EntityHuman) p1.getHandle()).pitch * 256.0F / 360.0F)));
	    setValue(packetTeleportDown, "g", true);

	    for (Player player : Bukkit.getOnlinePlayers())
	    {
	    	Location loc = p.getLocation().clone();
	    	player.sendBlockChange(loc.subtract(0, loc.getY(), 0), Material.RED_BED, (byte) 0);
	    	
	    	CraftPlayer pl = ((CraftPlayer) player);
	    	if (player != p)
	    	{
	    		pl.getHandle().playerConnection.sendPacket(packetInfo);
	    		pl.getHandle().playerConnection.sendPacket(packetEntitySpawn);
	    		pl.getHandle().playerConnection.sendPacket(packetTeleportDown);
	    		pl.getHandle().playerConnection.sendPacket(packetBed);
	    		pl.getHandle().playerConnection.sendPacket(packetTeleport);
	    	}
	    }
	    
	    dataList.clear();
	    entityID++;
	}
	
	@SuppressWarnings("deprecation")
	private void destroyCorpse (Player p)
	{
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(corpses.get(p).getValue());
		
		Block b = corpses.get(p).getKey().clone().subtract(0, 2, 0).getBlock();
		
		for (Player p2 : corpses.get(p).getKey().getWorld().getPlayers())
		{
			((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
			p2.sendBlockChange(b.getLocation(), b.getType(), b.getData());
		}
	}
	
	public static DataWatcher clonePlayerDatawatcher(Player player, int currentEntityID)
	{
		EntityHuman eh = new EntityHuman(((CraftWorld) player.getWorld()).getHandle(), ((CraftPlayer) player).getProfile())
			{
				public boolean u() {return false;}
				public boolean isSpectator() {return false;}
			};
		
		eh.f(currentEntityID);
		return eh.getDataWatcher();
	}
	
	<T> void setValue(Object instance, String fieldName, T value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field field = instance.getClass().getDeclaredField(fieldName);
		
		field.setAccessible(true);
		field.set(instance, value);
	}

	@Override
	public String description()
	{
		return "§6[AIDE] §2This option will spawn dead corpses lying on the ground when a player dies";
	}
}
