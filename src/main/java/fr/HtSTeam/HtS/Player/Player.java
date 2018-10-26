package fr.HtSTeam.HtS.Player;

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
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Events.Structure.EventHandler;
import fr.HtSTeam.HtS.Team.Team;
import fr.HtSTeam.HtS.Utils.Nms;
import fr.HtSTeam.HtS.Utils.PRIORITY;
import fr.HtSTeam.HtS.Utils.Tag;
import fr.HtSTeam.HtS.Utils.Files.XmlFile;

public class Player {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static HashMap<UUID, Player> uuids = new HashMap<UUID, Player>();
	/**
	 * Returns the list of all Player (not necessarily online).
	 * 
	 * @return ArrayList<Player>
	 */
	public static ArrayList<Player> getPlayers() { return players; }
	
	
	private ArrayList<Runnable> asynctasks = new ArrayList<Runnable>();
	
	private org.bukkit.entity.Player player;
	
	private UUID uuid;
	private String name;
	private String display_name;
	
	private Team team;
	private Team fake_team;
	
	private boolean op;
	private World world;
	private Location location;
	private Inventory inventory;
	private double health;
		
	
	/**
	 * Creates an independent player, highly modular and safe.
	 * 
	 * @param player 
	 * 			Bukkit Player
	 * @return Player
	 */
	private Player(org.bukkit.entity.Player player) {
		if (player == null)
			return;
		
		players.add(this);
		uuids.put(player.getUniqueId(), this);
		
		this.player = player;
		
		uuid = player.getUniqueId();
		name = player.getName();
		display_name = player.getDisplayName();
		
		op = player.isOp();
		world = player.getWorld();
		location = player.getLocation();
		inventory = player.getInventory();
		health = player.getHealth();
	}
	
	/**
	 * Creates an independent player, highly modular and safe. Cannot instantiate twice. 
	 * 
	 * @param player 
	 * 			Bukkit Player
	 * @return Player (new or already existing instance)
	 */
	public static Player instance(org.bukkit.entity.Player player) {
		if (player == null)
			return null;
		else if (uuids.containsKey(player.getUniqueId()))
			return uuids.get(player.getUniqueId());
		return new Player(player);	
	}
	/**
	 * Creates an independent player, highly modular and safe. Cannot instantiate twice. 
	 * 
	 * @param uuid 
	 * 			UUID of Player
	 * @return Player (new or already existing instance) <strong>[Can be Null]</strong>
	 */
	public static Player instance(UUID uuid) {
		if (uuids.containsKey(uuid))
			return uuids.get(uuid);
		else if (Bukkit.getPlayer(uuid) != null)
			return instance(Bukkit.getPlayer(uuid));
		else 
			return null;
	}
	
	
	/**
	 * Use this method to know whether you should run your method in case the player is offline. You can add your method to be executed when the player reconnects
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
	 * Event triggered when a player joined, executes the {@link #runAsyncTask() runAsyncTask} method.
	 * 
	 * @param e the event
	 * @param p the player
	 */
	@EventHandler(PRIORITY.PLAYER)
	public static void onPlayerJoin(PlayerJoinEvent e, Player p) {
		p.player = e.getPlayer();
		p.runAsyncTask();
	}
	
	
	/**
	 * Event triggered when a player left.
	 * 
	 * @param e the event
	 * @param p the player
	 */
	@EventHandler(PRIORITY.PLAYER)
	public static void onPlayerQuit(PlayerQuitEvent e, Player p) {
		org.bukkit.entity.Player bplayer = e.getPlayer();
		p.op = bplayer.isOp();
		p.world = bplayer.getWorld();
		p.location = bplayer.getLocation();
		p.inventory = bplayer.getInventory();
		p.health = bplayer.getHealth();
		
		p.save();
	}
	
	
	@SuppressWarnings("serial")
	private void save() {
		ArrayList<Field> fields = new ArrayList<Field>();
		for (int i = 5; i < 10; i++) {
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
	
//	EVENTS --------------------------------------------------------------------------------------

	
	

	@EventHandler(PRIORITY.PLAYER)
	public static void onWorld(PlayerChangedWorldEvent e, Player p) {
		p.world = e.getPlayer().getWorld();
	}
	
	
	
	
//	METHOS --------------------------------------------------------------------------------------
	
	
	// RETURN
	
	
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
	
	
	/**
	 * Returns the team of this Player.
	 * 
	 * @return TeamBuilder
	 */
	public Team getTeam() { return team; }
	
	
	/**
	 * Returns the fake team of this Player.
	 * 
	 * @return TeamBuilder
	 */
	public Team getFakeTeam() { return fake_team; }
	
	
	/**
	 * Returns true if this Player is op.
	 * 
	 * @return boolean
	 */
	public boolean isOp() { return op; }
	
	
	/**
	 * Returns world.
	 * 
	 * @return World
	 */
	public World getWorld() { return world; }
	
	
	/**
	 * Returns last known location.
	 * 
	 * @return Location
	 */
	public Location getLocation() {
		if (canExecute(false))
			location = player.getLocation();
		return location;
	}
	

	/**
	 * Returns inventory.
	 * 
	 * @return Inventory
	 */
	public Inventory getInventory() {
		if (canExecute(false))
			inventory = player.getInventory();
		return inventory;
	}
	
	
	/**
	 * Returns health value.
	 * 
	 * @return double
	 */
	public double getHealth() {
		if (canExecute(false))
			health = player.getHealth();
		return health;
	}
	
	
	// NO RETURN
	
	
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
		if (message == null || message.isEmpty() || !canExecute(addasync, message))
			return;
		player.sendMessage(message);
	}
	
	
	/**
	 * Sends a JSON message to the player if he is connected, else it will send it when he reconnects
	 * 
	 * @param msg_cmd the message to send to the player
	 * @param action run_command or suggest_command...
	 * 
	 * @deprecated msg_cmd and action will be merge for greater flexibility
	 */
	public void sendJsonMessage(final LinkedHashMap<String, String> msg_cmd, String action) { sendJsonMessage(msg_cmd, action, true); }
	/**
	 * Sends a JSON message to the player
	 * 
	 * @param msg_cmd the message to send to the player
	 * @param action run_command or suggest_command...
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
	 * 
	 * @deprecated msg_cmd and action will be merge for greater flexibility
	 */
	public void sendJsonMessage(final LinkedHashMap<String, String> msg_cmd, String action, boolean addasync) {
		if (msg_cmd == null || msg_cmd.isEmpty() || action == null || action.isEmpty() || !canExecute(addasync, msg_cmd, action))
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
		
		try {
			Object craftPlayer = Nms.craftPlayerClass.cast(player);
			Object packet;

			Object[] chatMessageTypes = Nms.chatMessageTypeClass.getEnumConstants();
			Object chatMessageType = null;
			for (Object obj : chatMessageTypes)
				if (obj.toString().equals("CHAT"))
					chatMessageType = obj;

			Object chatCompontentText = Nms.iChatBaseComponentChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, message);
			packet = Nms.packetPlayOutChatClass.getConstructor(new Class<?>[] { Nms.iChatBaseComponentClass, Nms.chatMessageTypeClass }).newInstance(chatCompontentText, chatMessageType);
			
			Method craftPlayerHandleMethod = Nms.craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", Nms.packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Sends a title to the player if he is connected, else it will send it when he reconnects
	 * 
	 * @param title the title to send to the player
	 * @param subtitle the subtitle to send to the player
	 * @param duration the duration of the title
	 */
	public void sendTitle(String title, String subtitle, int duration) { sendTitle(title, subtitle, duration, true); }
	/**
	 * Sends a title to the player
	 * 
	 * @param title the title to send to the player
	 * @param subtitle the subtitle to send to the player
	 * @param duration the duration of the title
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
	 */
	public void sendTitle(String title, String subtitle, int duration, boolean addasync) {
		if (title == null || title.isEmpty() || duration < 1 || !canExecute(addasync, title, subtitle, duration))
			return;
		
		try {
			Object craftPlayer = Nms.craftPlayerClass.cast(player);
			Object packet_title = null;
			Object packet_subtitle = null;
			Object packet_duration = null;
			
			Object[] titleTypes = Nms.packetPlayOutTitleEnumTitleActionClass.getEnumConstants();
			Object titleType = null;
			Object subtitleType = null;
			for (Object obj : titleTypes)
				if (obj.toString().equals("TITLE"))
					titleType = obj;
				else if (obj.toString().equals("SUBTITLE"))
					subtitleType = obj;
			
			Object titleCompontentText = Nms.iChatBaseComponentChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
			packet_title = Nms.packetPlayOutTitleClass.getConstructor(new Class<?>[] { Nms.packetPlayOutTitleEnumTitleActionClass, Nms.iChatBaseComponentClass }).newInstance(titleType, titleCompontentText);
			if (subtitle != null && !subtitle.isEmpty()) {
				Object subtitleCompontentText = Nms.iChatBaseComponentChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
				packet_subtitle = Nms.packetPlayOutTitleClass.getConstructor(new Class<?>[] { Nms.packetPlayOutTitleEnumTitleActionClass, Nms.iChatBaseComponentClass }).newInstance(subtitleType, subtitleCompontentText);
			}
			packet_duration = Nms.packetPlayOutTitleClass.getConstructor(new Class<?>[] { int.class, int.class, int.class }).newInstance(5, duration * 20, 5);
			
			Method craftPlayerHandleMethod = Nms.craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", Nms.packetClass);
			sendPacketMethod.invoke(playerConnection, packet_title);
			if (subtitle != null && !subtitle.isEmpty())
				sendPacketMethod.invoke(playerConnection, packet_subtitle);
			sendPacketMethod.invoke(playerConnection, packet_duration);
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
		if (message == null || message.isEmpty() || duration < 1 || !canExecute(addasync, message, duration))
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
		try {
			Object craftPlayer = Nms.craftPlayerClass.cast(player);
			Object packet;

			Object[] chatMessageTypes = Nms.chatMessageTypeClass.getEnumConstants();
			Object chatMessageType = null;
			for (Object obj : chatMessageTypes)
				if (obj.toString().equals("GAME_INFO"))
					chatMessageType = obj;
			
			Object chatCompontentText = Nms.chatComponentTextClass.getConstructor(new Class<?>[] { String.class }).newInstance(message);
			packet = Nms.packetPlayOutChatClass.getConstructor(new Class<?>[] { Nms.iChatBaseComponentClass, Nms.chatMessageTypeClass }).newInstance(chatCompontentText, chatMessageType);
			
			Method craftPlayerHandleMethod = Nms.craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", Nms.packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setTeam(Team team) {
		if (team != null)
			team.add(this);
		else
			this.team.remove(this);
		this.team = team;
	}
	
	public void setFakeTeam(Team fake_team) {
		if (fake_team != null)
			fake_team.add(this);
		else
			this.fake_team.remove(this);
		this.fake_team = fake_team;
	}
	
	
	
	public void openInventory(Inventory inventory) {
		if(canExecute(false))
			player.openInventory(inventory);
	}
	public void closeInventory() {
		if(canExecute(false))
			player.closeInventory();
	}
	public void setHealth(double health) {
		if(canExecute(false))
			player.setHealth(health);
	}
	public void playSound(Location location, Sound sound, int volume, int pitch) {
		if(canExecute(false))
			player.playSound(location, sound, volume, pitch);
	}
	public void addPotionEffect(PotionEffect potionEffect) {
		if(canExecute(false))
			player.addPotionEffect(potionEffect);
	}
}