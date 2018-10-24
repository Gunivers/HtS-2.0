package fr.HtSTeam.HtS.Options.Options.AtDeath;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.EndTrigger;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.Nms;

/**
 * @author SwiftLee
 * @author A~Z
 */
public class DeadBodyOption extends OptionBuilder<Boolean> implements EndTrigger
{
	private static final Class<?> CPlayer = Nms.craftPlayerClass;
	private static final Class<?> EHuman = Nms.entityHumanClass;
	private static final Class<?> CWorld = Nms.craftWorldClass;
	private static final Class<?> PacketInfo = Nms.packetPlayOutPlayerInfoClass;
	private static final Class<?> PlayerData = Nms.packetPlayOutPlayerInfo_PlayerInfoDataClass;

	private static HashMap<fr.HtSTeam.HtS.Player.Player, Entry<Location, Integer>> corpses = new HashMap<>();
	private static Integer entityID = 0;
	
	public DeadBodyOption()
	{
		super(Material.BONE, "Cadavres", "§2Activé", true, GUIRegister.atDeath, false);
	}

	@Override
	public void event(fr.HtSTeam.HtS.Player.Player p)
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
		for (fr.HtSTeam.HtS.Player.Player p : corpses.keySet())
		{
			destroyCorpse(p);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDie(PlayerDeathEvent event)
	{
		if (!this.getValue()) return;
		
		spawnCorpse(event.getEntity());
	}
	
	public static boolean spawnCorpse(Player p)
	{
		try
		{
			Object pos = Nms.blockPositionClass.getConstructor(int.class, int.class, int.class).newInstance(p.getLocation().getBlockX(), 0, p.getLocation().getBlockZ());
			//BlockPosition pos = new BlockPosition(p.getLocation().getBlockX(), 0, p.getLocation().getBlockZ());
			return spawnCorpse(p, pos);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException | SecurityException | IllegalArgumentException e)
		{
			return false;
		}
	}

	@Deprecated
	public static boolean spawnCorpse(Player p, Object pos) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException
	{
		fr.HtSTeam.HtS.Player.Player HtSPlayer = fr.HtSTeam.HtS.Player.Player.instance(p);
		if (HtSPlayer == null)
			return false;
		
		HashMap<Location, Integer> temp = new HashMap<>();
		temp.put(p.getLocation(), entityID.intValue());		//entityID.intValue() clones the instance so as to avoid synchronization issues.
		
		corpses.put(HtSPlayer, temp.entrySet().iterator().next());

		Constructor<?> ChatMessage = Nms.iChatBaseComponentClass.getConstructor(String.class, Object[].class);
		
		Method getHandle = CPlayer.getDeclaredMethod("getHandle");
		Method getUniqueId = CPlayer.getDeclaredMethod("getUniqueId");
		Method getName = CPlayer.getDeclaredMethod("getName");
		
		Field infoAction_ADD_PLAYER = Nms.packetPlayOutPlayerInfo_PlayerInfoActionEnum.getDeclaredField("ADD_PLAYER");
		Field gm_SURVIVAL = Nms.gamemodeEnum.getDeclaredField("SURVIVAL");
		
		Object cPlayer1 = Nms.craftPlayerClass.cast(p);
		//CraftPlayer p1 = (CraftPlayer) p;

		double locY = EHuman.getField("locY").getDouble(getHandle.invoke(cPlayer1));
		double locX = EHuman.getField("locX").getDouble(getHandle.invoke(cPlayer1));
		double locZ = EHuman.getField("locZ").getDouble(getHandle.invoke(cPlayer1));
		//double locY = ((EntityHuman) p1.getHandle()).locY;
		//double locX = ((EntityHuman) p1.getHandle()).locX;
		//double locZ = ((EntityHuman) p1.getHandle()).locZ;
		
		float yaw = EHuman.getField("yaw").getFloat(getHandle.invoke(cPlayer1));
		float pitch = EHuman.getField("pitch").getFloat(getHandle.invoke(cPlayer1));
		//float yaw = ((EntityHuman) p1.getHandle()).yaw;
		//float pitch = ((EntityHuman) p1.getHandle()).pitch;

		Object dataWatcher = clonePlayerDatawatcher(p, entityID);
		//DataWatcher dw = clonePlayerDatawatcher(p, entityID);
		// - dw.watch(10, p1.getHandle().getDataWatcher().getByte(10));

		Object gameProfile = Nms.gameProfileClass.getConstructors()[0].newInstance(getUniqueId.invoke(cPlayer1), getName.invoke(cPlayer1));
		//GameProfile prof = new GameProfile(p1.getUniqueId(), p1.getName());

		Object packetInfo = PacketInfo.getConstructors()[1].newInstance(infoAction_ADD_PLAYER);
		//PacketPlayOutPlayerInfo packetInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
		Object data = PlayerData.getConstructors()[0].newInstance(gameProfile, 0, gm_SURVIVAL, ChatMessage.newInstance("", new Object[0]));
		//PacketPlayOutPlayerInfo.PlayerInfoData data = packetInfo.new PlayerInfoData(prof, 0, EnumGamemode.SURVIVAL, new ChatMessage("", new Object[0]));
	    
		List<Object> dataList = new ArrayList<>();
		//List<PacketPlayOutPlayerInfo.PlayerInfoData> dataList = new ArrayList<>();
	    
		dataList.add(data);
		setValue(packetInfo, "b", dataList);

		Object packetEntitySpawn = Nms.packetPlayOutNamedEntitySpawnClass.getConstructor().newInstance();
	    //PacketPlayOutNamedEntitySpawn packetEntitySpawn = new PacketPlayOutNamedEntitySpawn();
	    setValue(packetEntitySpawn, "a", entityID);
	    setValue(packetEntitySpawn, "b", Nms.gameProfileClass.getDeclaredMethod("getId").invoke(gameProfile));
	    //setValue(packetEntitySpawn, "b", prof.getId());
	    setValue(packetEntitySpawn, "c", Math.floor(locX * 32D));
	    setValue(packetEntitySpawn, "d", Math.floor(locY * 32D));
	    setValue(packetEntitySpawn, "e", Math.floor(locZ * 32D));
	    setValue(packetEntitySpawn, "f", (byte) ((int) (yaw * 256.0F / 360.0F)));
	    setValue(packetEntitySpawn, "g", (byte) ((int) (pitch * 256.0F / 360.0F)));
	    setValue(packetEntitySpawn, "i", Nms.dataWatcherClass.cast(dataWatcher));
	    //setValue(packetEntitySpawn, "i", dw);

	    Object packetBed = Nms.packetPlayOutBedClass.getConstructor().newInstance();
	    //PacketPlayOutBed packetBed = new PacketPlayOutBed();
	    setValue(packetBed, "a", entityID);
	    setValue(packetBed, "b", pos);

	    Object packetTeleport = Nms.packetPlayOutEntityTeleportClass.getConstructor().newInstance();
	    //PacketPlayOutEntityTeleport packetTeleport = new PacketPlayOutEntityTeleport();
	    setValue(packetTeleport, "a", entityID);
	    setValue(packetTeleport, "b", Math.floor(locX * 32.0D));
	    setValue(packetTeleport, "c", Math.floor(locY * 32.0D));
	    setValue(packetTeleport, "d", Math.floor(locZ * 32.0D));
	    setValue(packetTeleport, "e", (byte) ((int) (yaw * 256.0F / 360.0F)));
	    setValue(packetTeleport, "f", (byte) ((int) (pitch * 256.0F / 360.0F)));
	    setValue(packetTeleport, "g", true);

	    Object packetTeleportDown = Nms.packetPlayOutEntityTeleportClass.getConstructor().newInstance();
	    //PacketPlayOutEntityTeleport packetTeleportDown = new PacketPlayOutEntityTeleport();
	    setValue(packetTeleportDown, "a", entityID);
	    setValue(packetTeleportDown, "b", Math.floor(locX * 32.0D));
	    setValue(packetTeleportDown, "c", 0);
	    setValue(packetTeleportDown, "d", Math.floor(locZ * 32.0D));
	    setValue(packetTeleportDown, "e", (byte) ((int) (yaw * 256.0F / 360.0F)));
	    setValue(packetTeleportDown, "f", (byte) ((int) (pitch * 256.0F / 360.0F)));
	    setValue(packetTeleportDown, "g", true);

	    for (Player player : Bukkit.getOnlinePlayers())
	    {
	    	Location loc = p.getLocation().clone();
	    	player.sendBlockChange(loc.subtract(0, loc.getY(), 0), Material.RED_BED, (byte) 0);
	    	
	    	if (player != p)
	    	{
		    	Object pl = CPlayer.cast(player);
		    	//CraftPlayer pl = ((CraftPlayer) player);
		    	
	    		Method sendPacket = Nms.playerConnectionClass.getDeclaredMethod("sendPacker", Nms.packetClass);
	    		Field playerConnection = Nms.entityPlayerClass.getDeclaredField("playerConnection");
	    		
	    		sendPacket.invoke(playerConnection.get(getHandle.invoke(pl)), packetInfo);
	    		sendPacket.invoke(playerConnection.get(getHandle.invoke(pl)), packetEntitySpawn);
	    		sendPacket.invoke(playerConnection.get(getHandle.invoke(pl)), packetTeleportDown);
	    		sendPacket.invoke(playerConnection.get(getHandle.invoke(pl)), packetBed);
	    		sendPacket.invoke(playerConnection.get(getHandle.invoke(pl)), packetTeleport);
	    		//pl.getHandle().playerConnection.sendPacket(packetInfo);
	    		//pl.getHandle().playerConnection.sendPacket(packetEntitySpawn);
	    		//pl.getHandle().playerConnection.sendPacket(packetTeleportDown);
	    		//pl.getHandle().playerConnection.sendPacket(packetBed);
	    		//pl.getHandle().playerConnection.sendPacket(packetTeleport);
	    	}
	    }
	    
	    dataList.clear();
	    entityID++;
	    
	    return true;
	}
	
	public static boolean destroyCorpse(fr.HtSTeam.HtS.Player.Player player)
	{
		try
		{
			return destroyCorpse(Bukkit.getPlayer(player.getUUID()));
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e)
		{
			return false;
		}
	}
	
	@Deprecated
	public static boolean destroyCorpse(Player p) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException
	{
		fr.HtSTeam.HtS.Player.Player player = fr.HtSTeam.HtS.Player.Player.instance(p);
			if (player == null) return false;
		
		Method getHandle = CPlayer.getDeclaredMethod("getHandle");
		Method sendPacket = Nms.playerConnectionClass.getDeclaredMethod("sendPacker", Nms.packetClass);
		Field playerConnection = Nms.entityPlayerClass.getDeclaredField("playerConnection");
		
		Object packet = Nms.packetPlayOutEntityDestroyClass.getConstructor(int[].class).newInstance(corpses.get(player).getValue());
		//PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(corpses.get(p).getValue());
		
		Block b = corpses.get(player).getKey().clone().subtract(0, 2, 0).getBlock();
		
		for (Player p2 : corpses.get(player).getKey().getWorld().getPlayers())
		{
			Object cp = CPlayer.cast(p2);
			sendPacket.invoke(playerConnection.get(getHandle.invoke(cp)), packet);
			//((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
			
			p2.sendBlockChange(b.getLocation(), b.getType(), b.getData());
		}
		
		corpses.remove(player);
		return true;
	}
	
	private static Object clonePlayerDatawatcher(Player player, int currentEntityID) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
	{
		Method getProfile = CPlayer.getDeclaredMethod("getProfile");
		Method getHandle = CWorld.getDeclaredMethod("getHandle");
		
		Object eh = EHuman.getConstructors()[0].newInstance(getHandle.invoke(CWorld.cast(player.getWorld())), getProfile.invoke(CPlayer.cast(player)));
		/**
		EntityHuman eh = new EntityHuman(((CraftWorld) player.getWorld()).getHandle(), ((CraftPlayer) player).getProfile())
			{
				public boolean u() {return false;}
				public boolean isSpectator() {return false;}
			};
		*/
		
		EHuman.getDeclaredMethod("f", int.class).invoke(eh, currentEntityID);
		//eh.f(currentEntityID);
		
		return Nms.dataWatcherClass.cast(EHuman.getDeclaredMethod("getHandle").invoke(eh));
		//return (DataWatcher) eh.getHandle();
	}
	
	private static <T> void setValue(Object instance, String fieldName, T value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
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
