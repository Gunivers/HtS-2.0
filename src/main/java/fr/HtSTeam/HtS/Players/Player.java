package fr.HtSTeam.HtS.Players;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Teams.TeamBuilder;
import fr.HtSTeam.HtS.Utils.Tag;
import fr.HtSTeam.HtS.Utils.Files.XmlFile;

public class Player {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static HashMap<UUID, Player> uuids = new HashMap<UUID, Player>();
	/**
	 * Returns the list of all Player (not necessarily online).
	 * 
	 * @return List of Player
	 */
	public static ArrayList<Player> getPlayers() { return players; }
	/**
	 * Returns the instance of Player from a UniqueId.
	 * Should not return null.
	 * 
	 * @param uuid
	 * 		UniqueId of Player
	 * @return Player
	 */
	public static Player getPlayerFromUUID(UUID uuid) { if (uuids.containsKey(uuid)) return uuids.get(uuid); return null; }
	
	
	private ArrayList<Runnable> asynctasks = new ArrayList<Runnable>();
	
	
	private UUID uuid;
	private String name;
	private String display_name;
	/**
	 * Returns the UUID of this Player.
	 * 
	 * @return UUID
	 */
	public UUID getUUID() { return uuid; }
	/**
	 * Returns the UUID of this Player.
	 * 
	 * @return String
	 */
	public String getName() { return name; }
	/**
	 * Returns the displayed name of this Player.
	 * 
	 * @return String
	 */
	public String getDisplay() { return display_name; }
	
	
	private TeamBuilder team;
	private TeamBuilder fake_team;
	/**
	 * Returns the team of this Player.
	 * 
	 * @return TeamBuilder
	 */
	public TeamBuilder getTeam() { return team; }
	
	/**
	 * Returns the fake team of this Player.
	 * 
	 * @return TeamBuilder
	 */
	public TeamBuilder getFakeTeam() { return fake_team; }
	
	
	/**
	 * Creates an independent player, highly modular and safe.
	 * 
	 * @param player 
	 * 			Bukkit Player
	 * @return Player
	 */
	private Player(org.bukkit.entity.Player player) {
		players.add(this);
		uuids.put(player.getUniqueId(), this);
		
		uuid = player.getUniqueId();
		name = player.getName();
		display_name = player.getDisplayName();
	}
	/**
	 * Creates an independent player, highly modular and safe. Cannot instantiate twice. 
	 * 
	 * @param player 
	 * 			Bukkit Player
	 * @return Player (new or already existing instance)
	 */
	public static Player instance(org.bukkit.entity.Player player) {
		if (uuids.containsKey(player.getUniqueId()))
			return uuids.get(player.getUniqueId());
		return new Player(player);	
	}
	
	/**
	 * Temporary, anyone to delete
	 * @param uuid
	 * @param name
	 * @param display_name
	 */
	public Player(UUID uuid, String name, String display_name) {
		players.add(this);
		uuids.put(uuid, this);
		
		this.uuid = uuid;
		this.name = name;
		this.display_name = display_name;
	}
	@SuppressWarnings("serial")
	public void save() {
		ArrayList<Field> fields = new ArrayList<Field>();
		for (int i = 3; i < getClass().getDeclaredFields().length; i++) {
			fields.add(getClass().getDeclaredFields()[i]);
		}
		
		new File(Main.plugin.getDataFolder() + "/" + "Players" + "/", uuid.toString()  + ".xml").delete();
		
		XmlFile f = new XmlFile("Players", uuid.toString());
		fields.forEach(field -> { try {
			field.setAccessible(true);
			Object value = field.get(this);
			if(value != null)
				f.add(new Tag("field", new HashMap<String, String>() {{ put("name", field.getName()); }}, new ArrayList<Tag>(){{ add(new Tag(value.toString(), null, null)); }}));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		f.save();
	}
	
	/**
	 * Use this method to whether you should run your method in case the player is offline. You can add your method to be executed when the player reconnects
	 * 	
	 * @param addasync should it add this method to run at the player reconnection
	 * @param args parameters' values of method (do not put addasync in args)
	 * @return
	 * 		true when the player is online <p></p>
	 * 		false when the player is offline
	 */
	private boolean canExecute(final boolean addasync, final Object... args) {
		if (Bukkit.getPlayer(uuid) != null)
			return true;
		else {
			if (addasync)
				addAsyncTask(args);
			return false;
		}
	}
	/**
	 * Add a method to be run when the player reconnects  
	 * 
	 * @param args
	 * 		parameters' values of method to run (do not put addasync in args)
	 */
	private void addAsyncTask(final Object... args) {
		Method m = null;
		for (Method o : getClass().getMethods())
			if (o.getName().equals(Thread.currentThread().getStackTrace()[3].getMethodName()) && o.getParameterCount() == args.length)
				m = o;
		
		if (m == null)
			return;

		final Method method = m;
		Runnable runnable = () -> {
			try {
				method.invoke(this, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		};
		asynctasks.add(runnable);
	}	
	/**
	 * Runs every method added via {@link #addAsyncTask(Object...) addAsyncTask}
	 */
	private void runAsyncTask() {
		if (asynctasks.isEmpty())
			return;
		asynctasks.forEach(task -> task.run());
		asynctasks.clear();
	}
	/**
	 * Event triggered when a player joined, executes the {@link #runAsyncTask() runAsyncTask} method
	 * 
	 * @param e the event
	 */
//	@EventHandler
	public static void onPlayerjoin(PlayerJoinEvent e) {
		getPlayerFromUUID(e.getPlayer().getUniqueId()).runAsyncTask();
	}
	
	
	
	
//	METHOS --------------------------------------------------------------------------------------
	
	
	
		
	/**
	 * Sends a message to the player if he is connected, else it will send it when he reconnects
	 * 
	 * @param message the message to send to the player
	 */
	public void sendMessage(String message) { sendMessage(message, true); }
	/**
	 * Sends a message to the player
	 * 
	 * @param message the message to send to the player
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
	 */
	public void sendMessage(String message, boolean addasync) {
		if (!canExecute(addasync, message))
			return;
		Bukkit.getPlayer(uuid).sendMessage(message);
	}
	
	
	/**
	 * Sends a JSON message to the player if he is connected, else it will send it when he reconnects
	 * 
	 * @param msg_cmd the message to send to the player
	 * @param action run_command or suggest_command...
	 * 
	 * @deprecated msg_cmd and action will be merge for greater flexibility
	 */
	public void sendJsonCommand(final LinkedHashMap<String, String> msg_cmd, String action) { sendJsonCommand(msg_cmd, action, true); }
	/**
	 * Sends a JSON message to the player
	 * 
	 * @param msg_cmd the message to send to the player
	 * @param action run_command or suggest_command...
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
	 * 
	 * @deprecated msg_cmd and action will be merge for greater flexibility
	 */
	public void sendJsonCommand(final LinkedHashMap<String, String> msg_cmd, String action, boolean addasync) {
		if (!canExecute(addasync, msg_cmd, action))
			return;
		String message = null;
		for (Entry<String, String> set : msg_cmd.entrySet())
			if (message == null) {
				if (set.getValue() != null)
					message = "[{\"text\":\"" + set.getKey() + "\",\"clickEvent\":{\"action\":\"" + action + "\",\"value\":\""
							+ set.getValue() + "\"}}";
				else
					message = "[{\"text\":\"" + set.getKey() + "\"}";
			} else {
				if (set.getValue() != null)
					message = message + ",{\"text\":\"" + set.getKey()
							+ "\",\"clickEvent\":{\"action\":\"" + action + "\",\"value\":\"" + set.getValue() + "\"}}";
				else
					message = message + ",{\"text\":\"" + set.getKey() + "\"}";
			}
		message += ']';
		org.bukkit.entity.Player player = Bukkit.getPlayer(uuid);
		String nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		try {
			Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
			Object craftPlayer = craftPlayerClass.cast(player);
			Object packet;
			Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
			Class<?> packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");

			Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
			Class<?> iChatBaseComponentChatSerializerClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent$ChatSerializer");
			try {
				Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
				Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
				Object chatMessageType = null;
				for (Object obj : chatMessageTypes) {
					if (obj.toString().equals("CHAT")) {
						chatMessageType = obj;
					}
				}
				Object chatCompontentText = iChatBaseComponentChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, message);
				packet = packetPlayOutChatClass
						.getConstructor(new Class<?>[] { iChatBaseComponentClass, chatMessageTypeClass })
						.newInstance(chatCompontentText, chatMessageType);
			} catch (ClassNotFoundException cnfe) {
				Object chatCompontentText = iChatBaseComponentChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, message);
				packet = packetPlayOutChatClass.getConstructor(new Class<?>[] { iChatBaseComponentClass, byte.class })
						.newInstance(chatCompontentText, (byte) 2);
			}
			Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Sends a message to the player in his Action Bar if he is connected, else it will send it when he reconnects
	 * 
	 * @param message the message to send to the player
	 * @param duration the duration (seconds) of the message to be displayed
	 */
	public void sendActionBar(String message, int duration) { sendActionBar(message, duration, true); }
	/**
	 * Sends a message to the player in his Action Bar
	 * 
	 * @param message the message to send to the player
	 * @param duration the duration (seconds) of the message to be displayed
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
	 */
	public void sendActionBar(String message, int duration, boolean addasync) {
		if (!canExecute(addasync, message, duration))
			return;
		
		actionBar(message);
		duration = 20 * duration;
		
		if (duration >= 0) {
			new BukkitRunnable() {
				@Override
				public void run() {
					actionBar("");
				}
			}.runTaskLater(Main.plugin, duration + 1);
		}

		while (duration > 40) {
			duration -= 40;
			new BukkitRunnable() {
				@Override
				public void run() {
					actionBar(message);
				}
			}.runTaskLater(Main.plugin, (long) duration);
		}
	}
	/**
	 * Sends a message to the player in his Action Bar
	 * 
	 * @param message the message to send to the player
	 */
	private void actionBar(String message) {
		org.bukkit.entity.Player player = Bukkit.getPlayer(uuid);
		String nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		try {
			Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
			Object craftPlayer = craftPlayerClass.cast(player);
			Object packet;
			Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
			Class<?> packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");

			Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
			Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
			try {
				Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
				Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
				Object chatMessageType = null;
				for (Object obj : chatMessageTypes) {
					if (obj.toString().equals("GAME_INFO")) {
						chatMessageType = obj;
					}
				}
				Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[] { String.class })
						.newInstance(message);
				packet = packetPlayOutChatClass
						.getConstructor(new Class<?>[] { iChatBaseComponentClass, chatMessageTypeClass })
						.newInstance(chatCompontentText, chatMessageType);
			} catch (ClassNotFoundException cnfe) {
				Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[] { String.class })
						.newInstance(message);
				packet = packetPlayOutChatClass.getConstructor(new Class<?>[] { iChatBaseComponentClass, byte.class })
						.newInstance(chatCompontentText, (byte) 2);
			}
			Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}